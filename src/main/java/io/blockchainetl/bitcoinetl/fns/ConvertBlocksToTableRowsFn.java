package io.blockchainetl.bitcoinetl.fns;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.bitcoinetl.domain.Block;
import io.blockchainetl.bitcoinetl.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class ConvertBlocksToTableRowsFn extends ErrorHandlingDoFn<Block, TableRow> {

    private static final Logger LOG = LoggerFactory.getLogger(ConvertBlocksToTableRowsFn.class);

    private String startTimestamp;
    private Long allowedTimestampSkewSeconds;

    public ConvertBlocksToTableRowsFn(String startTimestamp, Long allowedTimestampSkewSeconds) {
        this.startTimestamp = startTimestamp;
        this.allowedTimestampSkewSeconds = allowedTimestampSkewSeconds;
    }

    @Override
    protected void doProcessElement(ProcessContext c) throws IOException {
        Block block = c.element();

        TableRow row = new TableRow();
        row.set("hash", block.getHash());
        row.set("size", block.getSize());
        row.set("stripped_size", block.getStrippedSize());
        row.set("weight", block.getWeight());
        row.set("number", block.getNumber());
        row.set("version", block.getVersion());
        row.set("merkle_root", block.getMerkleRoot());

        if (block.getTimestamp() != null) {
            ZonedDateTime blockTimestamp = TimeUtils.convertToZonedDateTime(block.getTimestamp());
            row.set("timestamp", TimeUtils.formatTimestamp(blockTimestamp));
            row.set("timestamp_month", TimeUtils.formatDate(blockTimestamp.withDayOfMonth(1)));

            row.set("nonce", block.getNonce());
            row.set("bits", block.getBits());
            row.set("coinbase_param", block.getCoinbaseParam());
            row.set("transaction_count", block.getTransactionCount());

            ZonedDateTime startDateTime = TimeUtils.parseDateTime(this.startTimestamp);
            ZonedDateTime currentDateTime = ZonedDateTime.now();

            if (blockTimestamp.isAfter(startDateTime) || blockTimestamp.isEqual(startDateTime)) {
                LOG.info("Writing block " + block.getHash());
                c.output(row);
            } else if (ChronoUnit.MINUTES.between(blockTimestamp, currentDateTime) > this.allowedTimestampSkewSeconds) {
                LOG.error("Block timestamp " + blockTimestamp + " for block " + block.getHash() +
                    " exceeds the maximum allowed time skew.");
            } else {
                LOG.debug("Block timestamp for block " + block.getHash() + " is before the startTimestamp.");
            }
        } else {
            LOG.error("Block timestamp for block " + block.getHash() + " is null.");
        }
    }
}
