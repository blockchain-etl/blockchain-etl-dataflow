package io.blockchainetl.ethereum.domain;

import com.google.common.base.Objects;
import org.apache.avro.reflect.Nullable;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.math.BigInteger;
import java.util.List;

@DefaultCoder(AvroCoder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {

    @Nullable
    private String type;
    
    @Nullable
    private String hash;

    @Nullable
    private String nonce;

    @Nullable
    @JsonProperty("transaction_index")
    private Long transactionIndex;

    @Nullable
    @JsonProperty("from_address")
    private String fromAddress;

    @Nullable
    @JsonProperty("to_address")
    private String toAddress;

    @Nullable
    private BigInteger value;

    @Nullable
    private Long gas;
    
    @Nullable
    @JsonProperty("gas_price")
    private Long gasPrice;

    @Nullable
    private String input;

    @Nullable
    @JsonProperty("receipt_cumulative_gas_used")
    private Long receiptCumulativeGasUsed;

    @Nullable
    @JsonProperty("receipt_gas_used")
    private Long receiptGasUsed;

    @Nullable
    @JsonProperty("receipt_contract_address")
    private String receiptContractAddress;

    @Nullable
    @JsonProperty("receipt_root")
    private String receiptRoot;

    @Nullable
    @JsonProperty("receipt_status")
    private Long receiptStatus;
    
    @Nullable
    @JsonProperty("block_number")
    private Long blockNumber;

    @Nullable
    @JsonProperty("block_hash")
    private String blockHash;

    @Nullable
    @JsonProperty("block_timestamp")
    private Long blockTimestamp;

    @Nullable
    @JsonProperty("max_fee_per_gas")
    private Long maxFeePerGas;

    @Nullable
    @JsonProperty("max_priority_fee_per_gas")
    private Long maxPriorityFeePerGas;

    @Nullable
    @JsonProperty("transaction_type")
    private Long transactionType;

    @Nullable
    @JsonProperty("receipt_effective_gas_price")
    private Long receiptEffectiveGasPrice;

    @Nullable
    @JsonProperty("max_fee_per_blob_gas")
    private Long maxFeePerBlobGas;

    @Nullable
    @JsonProperty("blob_versioned_hashes")
    private List<String> blobVersionedHashes;

    @Nullable
    @JsonProperty("receipt_blob_gas_price")
    private Long receiptBlobGasPrice;

    @Nullable
    @JsonProperty("receipt_blob_gas_used")
    private Long receiptBlobGasUsed;

    public Transaction() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public Long getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(Long transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public Long getGas() {
        return gas;
    }

    public void setGas(Long gas) {
        this.gas = gas;
    }

    public Long getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(Long gasPrice) {
        this.gasPrice = gasPrice;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Long getReceiptCumulativeGasUsed() {
        return receiptCumulativeGasUsed;
    }

    public void setReceiptCumulativeGasUsed(Long receiptCumulativeGasUsed) {
        this.receiptCumulativeGasUsed = receiptCumulativeGasUsed;
    }

    public Long getReceiptGasUsed() {
        return receiptGasUsed;
    }

    public void setReceiptGasUsed(Long receiptGasUsed) {
        this.receiptGasUsed = receiptGasUsed;
    }

    public String getReceiptContractAddress() {
        return receiptContractAddress;
    }

    public void setReceiptContractAddress(String receiptContractAddress) {
        this.receiptContractAddress = receiptContractAddress;
    }

    public String getReceiptRoot() {
        return receiptRoot;
    }

    public void setReceiptRoot(String receiptRoot) {
        this.receiptRoot = receiptRoot;
    }

    public Long getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(Long receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public Long getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Long blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public Long getBlockTimestamp() {
        return blockTimestamp;
    }

    public void setBlockTimestamp(Long blockTimestamp) {
        this.blockTimestamp = blockTimestamp;
    }

    public Long getMaxFeePerGas() { return maxFeePerGas; }

    public void setMaxFeePerGas(Long maxFeePerGas) { this.maxFeePerGas = maxFeePerGas; }

    public Long getMaxPriorityFeePerGas() { return maxPriorityFeePerGas; }

    public void setMaxPriorityFeePerGas(Long maxPriorityFeePerGas) { this.maxPriorityFeePerGas = maxPriorityFeePerGas; }

    public Long getTransactionType() { return transactionType; }

    public void setTransactionType(Long transactionType) { this.transactionType = transactionType; }

    public Long getReceiptEffectiveGasPrice() { return receiptEffectiveGasPrice; }

    public void setReceiptEffectiveGasPrice(Long receiptEffectiveGasPrice) { this.receiptEffectiveGasPrice = receiptEffectiveGasPrice; }

    public Long getMaxFeePerBlobGas() {
        return maxFeePerBlobGas;
    }

    public void setMaxFeePerBlobGas(Long maxFeePerBlobGas) {
        this.maxFeePerBlobGas = maxFeePerBlobGas;
    }

    public List<String> getBlobVersionedHashes() {
        return blobVersionedHashes;
    }

    public void setBlobVersionedHashes(List<String> blobVersionedHashes) {
        this.blobVersionedHashes = blobVersionedHashes;
    }

    public Long getReceiptBlobGasPrice() {
        return receiptBlobGasPrice;
    }

    public void setReceiptBlobGasPrice(Long receiptBlobGasPrice) {
        this.receiptBlobGasPrice = receiptBlobGasPrice;
    }

    public Long getReceiptBlobGasUsed() {
        return receiptBlobGasUsed;
    }

    public void setReceiptBlobGasUsed(Long receiptBlobGasUsed) {
        this.receiptBlobGasUsed = receiptBlobGasUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction that = (Transaction) o;
        return Objects.equal(type, that.type) &&
            Objects.equal(hash, that.hash) &&
            Objects.equal(nonce, that.nonce) &&
            Objects.equal(transactionIndex, that.transactionIndex) &&
            Objects.equal(fromAddress, that.fromAddress) &&
            Objects.equal(toAddress, that.toAddress) &&
            Objects.equal(value, that.value) &&
            Objects.equal(gas, that.gas) &&
            Objects.equal(gasPrice, that.gasPrice) &&
            Objects.equal(input, that.input) &&
            Objects.equal(receiptCumulativeGasUsed, that.receiptCumulativeGasUsed) &&
            Objects.equal(receiptGasUsed, that.receiptGasUsed) &&
            Objects.equal(receiptContractAddress, that.receiptContractAddress) &&
            Objects.equal(receiptRoot, that.receiptRoot) &&
            Objects.equal(receiptStatus, that.receiptStatus) &&
            Objects.equal(blockNumber, that.blockNumber) &&
            Objects.equal(blockHash, that.blockHash) &&
            Objects.equal(blockTimestamp, that.blockTimestamp) &&
            Objects.equal(maxFeePerGas, that.maxFeePerGas) &&
            Objects.equal(maxPriorityFeePerGas, that.maxPriorityFeePerGas) &&
            Objects.equal(transactionType, that.transactionType) &&
            Objects.equal(receiptEffectiveGasPrice, that.receiptEffectiveGasPrice) &&
            Objects.equal(maxFeePerBlobGas, that.maxFeePerBlobGas) &&
            Objects.equal(blobVersionedHashes, that.blobVersionedHashes) &&
            Objects.equal(receiptBlobGasPrice, that.receiptBlobGasPrice) &&
            Objects.equal(receiptBlobGasUsed, that.receiptBlobGasUsed);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, hash, nonce, transactionIndex, fromAddress, toAddress, value, gas, gasPrice,
            input,
            receiptCumulativeGasUsed, receiptGasUsed, receiptContractAddress, receiptRoot, receiptStatus, blockNumber,
            blockHash, blockTimestamp, maxFeePerGas, maxPriorityFeePerGas, transactionType, receiptEffectiveGasPrice,
            maxFeePerBlobGas, blobVersionedHashes, receiptBlobGasPrice, receiptBlobGasUsed);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("type", type)
            .add("hash", hash)
            .add("nonce", nonce)
            .add("transactionIndex", transactionIndex)
            .add("fromAddress", fromAddress)
            .add("toAddress", toAddress)
            .add("value", value)
            .add("gas", gas)
            .add("gasPrice", gasPrice)
            .add("input", input)
            .add("receiptCumulativeGasUsed", receiptCumulativeGasUsed)
            .add("receiptGasUsed", receiptGasUsed)
            .add("receiptContractAddress", receiptContractAddress)
            .add("receiptRoot", receiptRoot)
            .add("receiptStatus", receiptStatus)
            .add("blockNumber", blockNumber)
            .add("blockHash", blockHash)
            .add("blockTimestamp", blockTimestamp)
            .add("maxFeePerGas", maxFeePerGas)
            .add("maxPriorityFeePerGas", maxPriorityFeePerGas)
            .add("transactionType", transactionType)
            .add("receiptEffectiveGasPrice", receiptEffectiveGasPrice)
            .add("maxFeePerBlobGas", maxFeePerBlobGas)
            .add("blobVersionedHashes", blobVersionedHashes)
            .add("receiptBlobGasPrice", receiptBlobGasPrice)
            .add("receiptBlobGasUsed", receiptBlobGasUsed)
            .toString();
    }
}
