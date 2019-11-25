package io.blockchainetl.eos;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.eos.fns.ConvertActionsToTableRowsFn;
import io.blockchainetl.eos.fns.ConvertBlocksToTableRowsFn;
import io.blockchainetl.eos.fns.ConvertTransactionsToTableRowsFn;
import io.blockchainetl.common.PubSubToBigQueryPipelineOptions;
import io.blockchainetl.common.domain.ChainConfig;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.blockchainetl.common.PubSubToBigQueryPipeline.readChainConfigs;
import static io.blockchainetl.common.PubSubToBigQueryPipeline.runPipeline;


public class EosPubSubToBigQueryPipeline {

    public static void main(String[] args) throws IOException, InterruptedException {
        PubSubToBigQueryPipelineOptions options =
            PipelineOptionsFactory.fromArgs(args).withValidation().as(PubSubToBigQueryPipelineOptions.class);

        runEosPipeline(options);
    }

    static void runEosPipeline(PubSubToBigQueryPipelineOptions options) throws InterruptedException {
        List<ChainConfig> chainConfigs = readChainConfigs(options.getChainConfigFile());
       
        Map<String, Class<? extends DoFn<String, TableRow>>> entityConfigs = new HashMap<>();
        entityConfigs.put("blocks", ConvertBlocksToTableRowsFn.class);
        entityConfigs.put("transactions", ConvertTransactionsToTableRowsFn.class);
        entityConfigs.put("actions", ConvertActionsToTableRowsFn.class);
        
        runPipeline(options,chainConfigs, entityConfigs);
    }
}
