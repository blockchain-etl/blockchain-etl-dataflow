package io.blockchainetl.bitcoinetl;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.bitcoinetl.domain.Block;
import io.blockchainetl.bitcoinetl.domain.Transaction;
import io.blockchainetl.bitcoinetl.fns.ConvertBlocksToTableRowsFn;
import io.blockchainetl.bitcoinetl.fns.ConvertTransactionsToTableRowsFn;
import io.blockchainetl.bitcoinetl.fns.ParseEntitiesFromJsonFn;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
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

        // Blocks

        PCollection<String> blocksFromPubSub = p.apply("ReadBlocksFromPubSub",
            PubsubIO.readStrings().fromSubscription(options.getDashPubSubSubscriptionPrefix() + ".blocks"));
        
        PCollection<TableRow> blocks = buildBlocksPipeline(
            "Dash", options.getDashStartTimestamp(), options.getAllowedTimestampSkewSeconds(), blocksFromPubSub
        );

        blocks.apply(
            "WriteBlocksToBigQuery",
            BigQueryIO.writeTableRows()
                .withoutValidation()
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
                .to(options.getDashBigQueryDataset() + ".blocks"));

        // Transactions

        PCollection<String> transactionsFromPubSub = p.apply("ReadTransactionsFromPubSub",
            PubsubIO.readStrings().fromSubscription(options.getDashPubSubSubscriptionPrefix() + ".transactions"));
        
        PCollection<TableRow> transactions = buildTransactionsPipeline(
            "Dash", options.getDashStartTimestamp(), options.getAllowedTimestampSkewSeconds(), transactionsFromPubSub
        );

        transactions.apply(
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

    public static PCollection<TableRow> buildBlocksPipeline(
        String namePrefix, String startTimestamp, long allowedTimestampSkew, PCollection<String> input
    ) {
        PCollection<Block> blocks = input
            .apply(namePrefix + "ParseBlocks", ParDo.of(new ParseEntitiesFromJsonFn<>(Block.class)))
            .setCoder(AvroCoder.of(Block.class));

        return blocks
            .apply(namePrefix + "ConvertBlocksToTableRows", 
                ParDo.of(new ConvertBlocksToTableRowsFn(startTimestamp, allowedTimestampSkew)));
    }

    public static PCollection<TableRow> buildTransactionsPipeline(
        String namePrefix, String startTimestamp, long allowedTimestampSkew, PCollection<String> input
    ) {
        PCollection<Transaction> transactions = input
            .apply(namePrefix + "ParseTransactions", ParDo.of(new ParseEntitiesFromJsonFn<>(Transaction.class)))
            .setCoder(AvroCoder.of(Transaction.class));

        return transactions
            .apply(namePrefix + "ConvertTransactionsToTableRows", 
                ParDo.of(new ConvertTransactionsToTableRowsFn(startTimestamp, allowedTimestampSkew)));
    }
}
