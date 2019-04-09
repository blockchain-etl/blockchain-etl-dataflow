package io.blockchainetl.bitcoin.fns;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.bitcoin.domain.Block;
import io.blockchainetl.common.utils.JsonUtils;
import io.blockchainetl.common.fns.ConvertEntitiesToTableRowsFn;

public class ConvertBlocksToTableRowsFn extends ConvertEntitiesToTableRowsFn {

    public ConvertBlocksToTableRowsFn(String startTimestamp, Long allowedTimestampSkewSeconds) {
        super(startTimestamp, allowedTimestampSkewSeconds, "", true);
    }

    public ConvertBlocksToTableRowsFn(String startTimestamp, Long allowedTimestampSkewSeconds, String logPrefix) {
        super(startTimestamp, allowedTimestampSkewSeconds, logPrefix, true);
    }

    @Override
    protected void populateTableRowFields(TableRow row, String element) {
        Block block = JsonUtils.parseJson(element, Block.class);
        
        row.set("hash", block.getHash());
        row.set("size", block.getSize());
        row.set("stripped_size", block.getStrippedSize());
        row.set("weight", block.getWeight());
        row.set("number", block.getNumber());
        row.set("version", block.getVersion());
        row.set("merkle_root", block.getMerkleRoot());
        row.set("nonce", block.getNonce());
        row.set("bits", block.getBits());
        row.set("coinbase_param", block.getCoinbaseParam());
        row.set("transaction_count", block.getTransactionCount());
    }
}
