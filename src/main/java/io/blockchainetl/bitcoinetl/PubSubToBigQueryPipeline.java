package io.blockchainetl.bitcoinetl;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.bitcoinetl.fns.ConvertBlocksToTableRowsFn;
import io.blockchainetl.bitcoinetl.fns.ConvertTransactionsToTableRowsFn;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
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

        runPipeline(options);
    }

    static void runPipeline(PubSubToBigQueryPipelineOptions options) throws InterruptedException {
        Pipeline p = Pipeline.create(options);

        // Blocks

        buildPipeline(
            p,
            "DashBlocks",
            options.getDashPubSubSubscriptionPrefix() + ".blocks",
            new ConvertBlocksToTableRowsFn(options.getDashStartTimestamp(), options.getAllowedTimestampSkewSeconds()),
            options.getDashBigQueryDataset() + ".blocks"
        );

        // Transactions

        buildPipeline(
            p,
            "DashTransactions",
            options.getDashPubSubSubscriptionPrefix() + ".transactions",
            new ConvertTransactionsToTableRowsFn(options.getDashStartTimestamp(), options.getAllowedTimestampSkewSeconds()),
            options.getDashBigQueryDataset() + ".transactions"
        );

        // Run pipeline
        
        PipelineResult pipelineResult = p.run();
        LOG.info(pipelineResult.toString());
    }

    public static PCollection<TableRow> buildPipeline(
        Pipeline p,
        String namePrefix,
        String pubSubSubSubscription,
        DoFn<String, TableRow> convertFn,
        String bigQueryTable
    ) {
        PCollection<String> inputFromPubSub = p.apply(namePrefix + "ReadFromPubSub",
            PubsubIO.readStrings().fromSubscription(pubSubSubSubscription));

        PCollection<TableRow> tableRows = buildPipeline(
            namePrefix,
            inputFromPubSub,
            convertFn
        );

        tableRows.apply(
            namePrefix + "WriteToBigQuery",
            BigQueryIO.writeTableRows()
                .withoutValidation()
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
                .to(bigQueryTable));

        return tableRows;
    }

    public static PCollection<TableRow> buildPipeline(
        String namePrefix, 
        PCollection<String> input,
        DoFn<String, TableRow> convertFn
    ) {
        return input.apply(namePrefix + "ConvertToTableRows", ParDo.of(convertFn));
    }
}
