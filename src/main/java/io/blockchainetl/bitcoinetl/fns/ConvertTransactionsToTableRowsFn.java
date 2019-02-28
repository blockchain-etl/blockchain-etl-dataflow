package io.blockchainetl.bitcoinetl.fns;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.bitcoinetl.domain.Transaction;
import io.blockchainetl.bitcoinetl.domain.TransactionInput;
import io.blockchainetl.bitcoinetl.domain.TransactionOutput;
import io.blockchainetl.bitcoinetl.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ConvertTransactionsToTableRowsFn extends ErrorHandlingDoFn<Transaction, TableRow> {

    private static final Logger LOG = LoggerFactory.getLogger(ConvertTransactionsToTableRowsFn.class);
    
    private String startTimestamp;
    private Long allowedTimestampSkewSeconds;

    public ConvertTransactionsToTableRowsFn(
        String startTimestamp,
        Long allowedTimestampSkewSeconds) {
        this.startTimestamp = startTimestamp;
        this.allowedTimestampSkewSeconds = allowedTimestampSkewSeconds;
    }

    @Override
    protected void doProcessElement(ProcessContext c) {
        Transaction transaction = c.element();

        TableRow row = new TableRow();
        row.set("hash", transaction.getHash());
        row.set("size", transaction.getSize());
        row.set("virtual_size", transaction.getVirtualSize());
        row.set("version", transaction.getVersion());
        row.set("lock_time", transaction.getLockTime());
        row.set("block_hash", transaction.getBlockHash());
        row.set("block_number", transaction.getBlockNumber());

        if (transaction.getBlockTimestamp() != null) {
            ZonedDateTime blockTimestamp = TimeUtils.convertToZonedDateTime(transaction.getBlockTimestamp());
            row.set("block_timestamp", TimeUtils.formatTimestamp(blockTimestamp));
            row.set("block_timestamp_month", TimeUtils.formatDate(blockTimestamp.withDayOfMonth(1)));

            row.set("input_count", transaction.getInputCount());
            row.set("output_count", transaction.getOutputCount());
            row.set("input_value", transaction.getInputValue());
            row.set("output_value", transaction.getOutputValue());
            row.set("is_coinbase", transaction.getCoinbase());
            row.set("fee", transaction.getFee());

            List<TableRow> inputTableRows = convertInputs(transaction.getInputs());
            row.set("inputs", inputTableRows);

            List<TableRow> outputTableRows = convertOutputs(transaction.getOutputs());
            row.set("outputs", outputTableRows);

            ZonedDateTime startDateTime = TimeUtils.parseDateTime(this.startTimestamp);
            ZonedDateTime currentDateTime = ZonedDateTime.now();
            
            if (blockTimestamp.isAfter(startDateTime) || blockTimestamp.isEqual(startDateTime)) {
                LOG.info("Writing transaction " + transaction.getHash());
                c.output(row);
            } else if (ChronoUnit.MINUTES.between(blockTimestamp, currentDateTime) > this.allowedTimestampSkewSeconds) {
                LOG.error("Block timestamp " + blockTimestamp + " for transaction " + transaction.getHash() + 
                    " exceeds the maximum allowed time skew.");
            } else {
                LOG.debug("Block timestamp for transaction " + transaction.getHash() + " is before the startTimestamp.");
            }
        } else {
            LOG.error("Block timestamp for transaction " + transaction.getHash() + " is null.");
        }
    }

    private List<TableRow> convertInputs(List<TransactionInput> inputs) {
        List<TableRow> result = new ArrayList<>();
        if (inputs != null) {
            for (TransactionInput input : inputs) {
                TableRow tableRow = new TableRow();
                tableRow.set("index", input.getIndex());
                tableRow.set("spent_transaction_hash", input.getSpentTransactionHash());
                tableRow.set("spent_output_index", input.getSpentOutputIndex());
                tableRow.set("script_asm", input.getScriptAsm());
                tableRow.set("script_hex", input.getScriptHex());
                tableRow.set("sequence", input.getSequence());
                tableRow.set("required_signatures", input.getRequiredSignatures());
                tableRow.set("type", input.getType());
                tableRow.set("addresses", input.getAddresses());
                tableRow.set("value", input.getValue());
                result.add(tableRow);
            }
        }
        return result;
    }

    private List<TableRow> convertOutputs(List<TransactionOutput> outputs) {
        List<TableRow> result = new ArrayList<>();
        if (outputs != null) {
            for (TransactionOutput output : outputs) {
                TableRow tableRow = new TableRow();
                tableRow.set("index", output.getIndex());
                tableRow.set("script_asm", output.getScriptAsm());
                tableRow.set("script_hex", output.getScriptHex());
                tableRow.set("required_signatures", output.getRequiredSignatures());
                tableRow.set("type", output.getType());
                tableRow.set("addresses", output.getAddresses());
                tableRow.set("value", output.getValue());
                result.add(tableRow);
            }
        }
        return result;
    }
}
