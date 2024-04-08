package io.blockchainetl.ethereum.fns;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.ethereum.domain.Transaction;
import io.blockchainetl.common.fns.ConvertEntitiesToTableRowsFn;
import io.blockchainetl.common.utils.JsonUtils;

public class ConvertTransactionsToTableRowsFn extends ConvertEntitiesToTableRowsFn {

    public ConvertTransactionsToTableRowsFn(String startTimestamp, Long allowedTimestampSkewSeconds) {
        super(startTimestamp, allowedTimestampSkewSeconds, "", false);
    }

    public ConvertTransactionsToTableRowsFn(String startTimestamp, Long allowedTimestampSkewSeconds, String logPrefix) {
        super(startTimestamp, allowedTimestampSkewSeconds, logPrefix, false);
    }

    @Override
    protected void populateTableRowFields(TableRow row, String element) {
        Transaction transaction = JsonUtils.parseJson(element, Transaction.class);

        row.set("hash", transaction.getHash());
        row.set("nonce", transaction.getNonce());
        row.set("transaction_index", transaction.getTransactionIndex());
        row.set("from_address", transaction.getFromAddress());
        row.set("to_address", transaction.getToAddress());
        row.set("value", transaction.getValue() != null ? transaction.getValue().toString() : null);
        row.set("gas", transaction.getGas());
        row.set("gas_price", transaction.getGasPrice());
        row.set("input", transaction.getInput());
        row.set("receipt_cumulative_gas_used", transaction.getReceiptCumulativeGasUsed());
        row.set("receipt_gas_used", transaction.getReceiptGasUsed());
        row.set("receipt_contract_address", transaction.getReceiptContractAddress());
        row.set("receipt_root", transaction.getReceiptRoot());
        row.set("receipt_status", transaction.getReceiptStatus());
        row.set("block_number", transaction.getBlockNumber());
        row.set("block_hash", transaction.getBlockHash());
        row.set("max_fee_per_gas", transaction.getMaxFeePerGas());
        row.set("max_priority_fee_per_gas", transaction.getMaxPriorityFeePerGas());
        row.set("transaction_type", transaction.getTransactionType());
        row.set("receipt_effective_gas_price", transaction.getReceiptEffectiveGasPrice());
        row.set("max_fee_per_blob_gas", transaction.getMaxFeePerBlobGas());
        row.set("blob_versioned_hashes", transaction.getBlobVersionedHashes());
        row.set("receipt_blob_gas_price", transaction.getReceiptBlobGasPrice());
        row.set("receipt_blob_gas_used", transaction.getReceiptBlobGasUsed());
    }
}
