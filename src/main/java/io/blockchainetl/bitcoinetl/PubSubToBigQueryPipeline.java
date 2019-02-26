package io.blockchainetl.bitcoinetl;

import com.google.api.services.bigquery.model.TableRow;
import com.google.common.collect.ImmutableMap;
import io.blockchainetl.bitcoinetl.domain.Block;
import io.blockchainetl.bitcoinetl.domain.Transaction;
import io.blockchainetl.bitcoinetl.fns.ConvertBlocksToTableRowsFn;
import io.blockchainetl.bitcoinetl.fns.ConvertTransactionsToTableRowsFn;
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
import java.util.Map;

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
        
        Map<String, PCollection<TableRow>> tableRows = buildPipeline(options.getDashStartTimestamp(), blockchainData);

        // Write output
        
        tableRows.get("block").apply(
            "WriteBlocksToBigQuery",
            BigQueryIO.writeTableRows()
                .withoutValidation()
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
                .to(options.getDashBigQueryDataset() + ".blocks"));

        tableRows.get("transaction").apply(
            "WriteTransactionsToBigQuery",
            BigQueryIO.writeTableRows()
                .withoutValidation()
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
                .to(options.getDashBigQueryDataset() + ".transactions"));

        // Run pipeline
        
        PipelineResult pipelineResult = p.run();
        LOG.info(pipelineResult.toString());
    }

    public static Map<String, PCollection<TableRow>> buildPipeline(String startTimestamp, PCollection<String> blockchainData) {
        // Blocks
        
        PCollection<Block> blocks = blockchainData
            .apply("FilterBlocks", Filter.by(new FilterMessagesByTypePredicate("block")))
            .apply("ParseBlocks", ParDo.of(new ParseEntitiesFromJsonFn<>(Block.class)))
            .setCoder(AvroCoder.of(Block.class));

        PCollection<TableRow> blockTableRows = blocks
            .apply("ConvertBlocksToTableRows", ParDo.of(new ConvertBlocksToTableRowsFn(startTimestamp)));

        // Transaction
        
        PCollection<Transaction> transactions = blockchainData
            .apply("FilterTransactions", Filter.by(new FilterMessagesByTypePredicate("transaction")))
            .apply("ParseTransactions", ParDo.of(new ParseEntitiesFromJsonFn<>(Transaction.class)))
            .setCoder(AvroCoder.of(Transaction.class));

        PCollection<TableRow> transactionTableRows = transactions
            .apply("ConvertTransactionsToTableRows", ParDo.of(new ConvertTransactionsToTableRowsFn(startTimestamp)));
        
        return ImmutableMap.of(
            "block", blockTableRows,
            "transaction", transactionTableRows
        );
    }
}
