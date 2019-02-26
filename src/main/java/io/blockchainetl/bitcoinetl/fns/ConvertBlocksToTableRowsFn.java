package io.blockchainetl.bitcoinetl.fns;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.bitcoinetl.domain.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertBlocksToTableRowsFn extends ErrorHandlingDoFn<Block, TableRow> {

    private static final Logger LOG = LoggerFactory.getLogger(ConvertBlocksToTableRowsFn.class);

    private static final ZoneId UTC = ZoneId.of("UTC");
    
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter
        .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(UTC);
    private static final DateTimeFormatter TIMESTAMP_MONTH_FORMATTER = DateTimeFormatter
        .ofPattern("yyyy-MM-dd").withZone(ZoneId.of("UTC"));

    private String startTimestamp;

    public ConvertBlocksToTableRowsFn(String startTimestamp) {
        this.startTimestamp = startTimestamp;
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
            Instant blockInstant = Instant.ofEpochSecond(block.getTimestamp());
            ZonedDateTime blockTimestamp = ZonedDateTime.from(blockInstant.atZone(UTC));
            row.set("timestamp", TIMESTAMP_FORMATTER.format(blockTimestamp));
            row.set("timestamp_month", TIMESTAMP_MONTH_FORMATTER.format(blockTimestamp.withDayOfMonth(1)));

            row.set("nonce", block.getNonce());
            row.set("bits", block.getBits());
            row.set("coinbase_param", block.getCoinbaseParam());
            row.set("transaction_count", block.getTransactionCount());

            ZonedDateTime startDateTime = ZonedDateTime.parse(this.startTimestamp, TIMESTAMP_FORMATTER);
            
            if (blockTimestamp.isAfter(startDateTime) || blockTimestamp.isEqual(startDateTime)) {
                LOG.info("Writing block " + block.getHash());
                c.output(row);
            } else {
                LOG.debug("Block time for block " + block.getHash() + " is before the startTimestamp.");
            }
        } else {
            LOG.error("Block timestamp for block " + block.getHash() + " is null.");
        }
    }
}
