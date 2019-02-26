package io.blockchainetl.bitcoinetl;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.bitcoinetl.domain.Block;
import io.blockchainetl.bitcoinetl.fns.ConvertBlocksToTableRowsFn;
import io.blockchainetl.bitcoinetl.fns.FilterMessagesByTypePredicate;
import io.blockchainetl.bitcoinetl.fns.ParseEntitiesFromJsonFn;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static io.blockchainetl.bitcoinetl.utils.ConfigUtils.expandArgs;


public class PubSubToBigQueryPipeline {

    private static final Logger LOG = LoggerFactory.getLogger(PubSubToBigQueryPipeline.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        PubSubToBigQueryPipelineOptions options =
            PipelineOptionsFactory.fromArgs(expandArgs(args)).withValidation().as(PubSubToBigQueryPipelineOptions.class);

        runEthereumPipeline(options);
    }

    static void runEthereumPipeline(PubSubToBigQueryPipelineOptions options) throws InterruptedException {
        Pipeline p = Pipeline.create(options);
        
        // Read input

        PCollection<String> blockchainData = p.apply("ReadBlockchainDataFromPubSub",
            PubsubIO.readStrings().fromSubscription(options.getDashPubSubSubscription()));

        // Build pipeline
        
        PCollection<TableRow> tableRows = buildPipeline(options.getDashStartTimestamp(), blockchainData);

        // Write output
        
        tableRows.apply(
            "WriteRecordsToBigQuery",
            BigQueryIO.writeTableRows()
                .withoutValidation()
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
                .to(options.getDashBigQueryTable()));

        // Run pipeline
        
        PipelineResult pipelineResult = p.run();
        LOG.info(pipelineResult.toString());
    }

    public static PCollection<TableRow> buildPipeline(String startTimestamp, PCollection<String> blockchainData) {
        PCollection<Block> blocks = blockchainData
            .apply("FilterBlocks", Filter.by(new FilterMessagesByTypePredicate("block")))
            .apply("ParseBlocks", ParDo.of(new ParseEntitiesFromJsonFn<>(Block.class)))
            .setCoder(AvroCoder.of(Block.class));

        PCollection<TableRow> tableRows = blocks
            .apply("ConvertBlocksToTableRows", ParDo.of(new ConvertBlocksToTableRowsFn(startTimestamp)));
        
        return tableRows;
    }
}
