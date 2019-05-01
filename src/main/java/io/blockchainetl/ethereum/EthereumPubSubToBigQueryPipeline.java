package io.blockchainetl.ethereum;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.common.PubSubToBigQueryPipelineOptions;
import io.blockchainetl.common.domain.ChainConfig;
import io.blockchainetl.ethereum.fns.ConvertBlocksToTableRowsFn;
import io.blockchainetl.ethereum.fns.ConvertContractsToTableRowsFn;
import io.blockchainetl.ethereum.fns.ConvertLogsToTableRowsFn;
import io.blockchainetl.ethereum.fns.ConvertTokenTransfersToTableRowsFn;
import io.blockchainetl.ethereum.fns.ConvertTokensToTableRowsFn;
import io.blockchainetl.ethereum.fns.ConvertTracesToTableRowsFn;
import io.blockchainetl.ethereum.fns.ConvertTransactionsToTableRowsFn;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.blockchainetl.common.PubSubToBigQueryPipeline.readChainConfigs;
import static io.blockchainetl.common.PubSubToBigQueryPipeline.runPipeline;


public class EthereumPubSubToBigQueryPipeline {

    public static void main(String[] args) throws IOException, InterruptedException {
        PubSubToBigQueryPipelineOptions options =
            PipelineOptionsFactory.fromArgs(args).withValidation().as(PubSubToBigQueryPipelineOptions.class);

        runEthereumPipeline(options);
    }

    static void runEthereumPipeline(PubSubToBigQueryPipelineOptions options) {
        List<ChainConfig> chainConfigs = readChainConfigs(options.getChainConfigFile());

        Map<String, Class<? extends DoFn<String, TableRow>>> entityConfigs = new HashMap<>();
        entityConfigs.put("blocks", ConvertBlocksToTableRowsFn.class);
        entityConfigs.put("transactions", ConvertTransactionsToTableRowsFn.class);
        entityConfigs.put("logs", ConvertLogsToTableRowsFn.class);
        entityConfigs.put("token_transfers", ConvertTokenTransfersToTableRowsFn.class);
        entityConfigs.put("traces", ConvertTracesToTableRowsFn.class);
        entityConfigs.put("contracts", ConvertContractsToTableRowsFn.class);
        entityConfigs.put("tokens", ConvertTokensToTableRowsFn.class);
        runPipeline(options,chainConfigs, entityConfigs);
    }
}
