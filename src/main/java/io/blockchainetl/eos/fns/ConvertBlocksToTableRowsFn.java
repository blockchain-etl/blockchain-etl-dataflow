package io.blockchainetl.eos.fns;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.common.fns.ConvertEntitiesToTableRowsFn;
import io.blockchainetl.common.fns.TimestampParserRepository;
import io.blockchainetl.common.utils.JsonUtils;
import io.blockchainetl.eos.domain.Block;

public class ConvertBlocksToTableRowsFn extends ConvertEntitiesToTableRowsFn {

    public ConvertBlocksToTableRowsFn(String startTimestamp, Long allowedTimestampSkewSeconds) {
        super(startTimestamp, allowedTimestampSkewSeconds, "", false,
            TimestampParserRepository.KEY_EOS);
    }

    public ConvertBlocksToTableRowsFn(String startTimestamp, Long allowedTimestampSkewSeconds, String logPrefix) {
        super(startTimestamp, allowedTimestampSkewSeconds, logPrefix, false,
            TimestampParserRepository.KEY_EOS);
    }

    @Override
    protected void populateTableRowFields(TableRow row, String element) {
        Block block = JsonUtils.parseJson(element, Block.class);

        row.set("number", block.getNumber());
        row.set("hash", block.getHash());
        row.set("action_mroot", block.getActionMroot());
        row.set("transaction_mroot", block.getTransactionMroot());
        row.set("producer", block.getProducer());
        row.set("transaction_count", block.getTransactionCount());
    }
}
