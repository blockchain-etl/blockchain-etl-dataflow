package io.blockchainetl.bitcoinetl;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.bitcoinetl.domain.ChainConfig;
import io.blockchainetl.bitcoinetl.fns.ConvertBlocksToTableRowsFn;
import io.blockchainetl.bitcoinetl.fns.ConvertTransactionsToTableRowsFn;
import io.blockchainetl.bitcoinetl.utils.FileUtils;
import io.blockchainetl.bitcoinetl.utils.JsonUtils;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.bigquery.InsertRetryPolicy;
import org.apache.beam.sdk.io.gcp.bigquery.WriteResult;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;


public class PubSubToBigQueryPipeline {

    private static final Logger LOG = LoggerFactory.getLogger(PubSubToBigQueryPipeline.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        PubSubToBigQueryPipelineOptions options =
            PipelineOptionsFactory.fromArgs(args).withValidation().as(PubSubToBigQueryPipelineOptions.class);

        runPipeline(options);
    }

    static void runPipeline(PubSubToBigQueryPipelineOptions options) throws InterruptedException {
        Pipeline p = Pipeline.create(options);

        
        List<ChainConfig> chainConfigs = readChainConfigs(options.getChainConfigFile());
        
        if (chainConfigs.isEmpty()) {
            throw new RuntimeException("Chain configs can't be empty");
        }

        for (ChainConfig chainConfig : chainConfigs) {
            // Blocks

            buildPipeline(
                p,
                chainConfig.getTransformNamePrefix() + "Blocks",
                chainConfig.getPubSubSubscriptionPrefix() + ".blocks",
                new ConvertBlocksToTableRowsFn(chainConfig.getStartTimestamp(), options.getAllowedTimestampSkewSeconds(),
                    chainConfig.getTransformNamePrefix() + ": "),
                chainConfig.getBigQueryDataset() + ".blocks"
            );

            // Transactions

            buildPipeline(
                p,
                chainConfig.getTransformNamePrefix() + "Transactions",
                chainConfig.getPubSubSubscriptionPrefix() + ".transactions",
                new ConvertTransactionsToTableRowsFn(chainConfig.getStartTimestamp(), options.getAllowedTimestampSkewSeconds(),
                    chainConfig.getTransformNamePrefix() + ": "),
                chainConfig.getBigQueryDataset() + ".transactions"
            );  
        }

        // Run pipeline
        
        PipelineResult pipelineResult = p.run();
        LOG.info(pipelineResult.toString());
    }

    private static List<ChainConfig> readChainConfigs(String file) {
        String fileContents = FileUtils.readFile(file, Charset.forName("UTF-8"));
        List<ChainConfig> result = JsonUtils.parseJson(fileContents, new TypeReference<List<ChainConfig>>() {
        });
        return result;
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

        WriteResult writeResult = tableRows.apply(
            namePrefix + "WriteToBigQuery",
            BigQueryIO.writeTableRows()
                .withoutValidation()
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
                .withFailedInsertRetryPolicy(InsertRetryPolicy.retryTransientErrors())
                .to(bigQueryTable));

        // There is a limit in BigQuery of 1MB per record for streaming inserts. To handle such cases we use file loads
        // where the limit is 100MB per record.
        writeResult.getFailedInserts().apply(
            namePrefix + "TryWriteFailedRecordsToBigQuery",
            BigQueryIO.writeTableRows()
                .withoutValidation()
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
                .withMethod(BigQueryIO.Write.Method.FILE_LOADS)
                .withNumFileShards(1)
                .withTriggeringFrequency(Duration.standardMinutes(10))
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
