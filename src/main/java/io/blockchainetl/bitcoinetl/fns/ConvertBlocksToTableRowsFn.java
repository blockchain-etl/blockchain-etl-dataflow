package io.blockchainetl.bitcoinetl.fns;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.bitcoinetl.domain.Block;
import io.blockchainetl.bitcoinetl.utils.JsonUtils;

public class ConvertBlocksToTableRowsFn extends ConvertEntitiesToTableRowsFn {

    public ConvertBlocksToTableRowsFn(String startTimestamp, Long allowedTimestampSkewSeconds) {
        super(startTimestamp, allowedTimestampSkewSeconds);
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
