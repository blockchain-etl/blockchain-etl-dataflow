package io.blockchainetl.bitcoin.domain;

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
    private Long size;
    
    @Nullable
    @JsonProperty("virtual_size")
    private Long virtualSize;
    
    @Nullable
    private Long version;

    @Nullable
    @JsonProperty("lock_time")
    private String lockTime;

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
    @JsonProperty("is_coinbase")
    private Boolean isCoinbase;

    @Nullable
    @JsonProperty("input_count")
    private Long inputCount;

    @Nullable
    @JsonProperty("output_count")
    private Long outputCount;

    @Nullable
    @JsonProperty("input_value")
    private BigInteger inputValue;

    @Nullable
    @JsonProperty("output_value")
    private BigInteger outputValue;

    @Nullable
    private BigInteger fee;

    @Nullable
    private List<TransactionInput> inputs;

    @Nullable
    private List<TransactionOutput> outputs;

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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getVirtualSize() {
        return virtualSize;
    }

    public void setVirtualSize(Long virtualSize) {
        this.virtualSize = virtualSize;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getLockTime() {
        return lockTime;
    }

    public void setLockTime(String lockTime) {
        this.lockTime = lockTime;
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

    public Boolean getCoinbase() {
        return isCoinbase;
    }

    public void setCoinbase(Boolean coinbase) {
        isCoinbase = coinbase;
    }

    public Long getInputCount() {
        return inputCount;
    }

    public void setInputCount(Long inputCount) {
        this.inputCount = inputCount;
    }

    public Long getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(Long outputCount) {
        this.outputCount = outputCount;
    }

    public BigInteger getInputValue() {
        return inputValue;
    }

    public void setInputValue(BigInteger inputValue) {
        this.inputValue = inputValue;
    }

    public BigInteger getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(BigInteger outputValue) {
        this.outputValue = outputValue;
    }

    public BigInteger getFee() {
        return fee;
    }

    public void setFee(BigInteger fee) {
        this.fee = fee;
    }

    public List<TransactionInput> getInputs() {
        return inputs;
    }

    public void setInputs(List<TransactionInput> inputs) {
        this.inputs = inputs;
    }

    public List<TransactionOutput> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<TransactionOutput> outputs) {
        this.outputs = outputs;
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
            Objects.equal(size, that.size) &&
            Objects.equal(virtualSize, that.virtualSize) &&
            Objects.equal(version, that.version) &&
            Objects.equal(lockTime, that.lockTime) &&
            Objects.equal(blockNumber, that.blockNumber) &&
            Objects.equal(blockHash, that.blockHash) &&
            Objects.equal(blockTimestamp, that.blockTimestamp) &&
            Objects.equal(isCoinbase, that.isCoinbase) &&
            Objects.equal(inputCount, that.inputCount) &&
            Objects.equal(outputCount, that.outputCount) &&
            Objects.equal(inputValue, that.inputValue) &&
            Objects.equal(outputValue, that.outputValue) &&
            Objects.equal(fee, that.fee) &&
            Objects.equal(inputs, that.inputs) &&
            Objects.equal(outputs, that.outputs);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, hash, size, virtualSize, version, lockTime, blockNumber, blockHash,
            blockTimestamp,
            isCoinbase, inputCount, outputCount, inputValue, outputValue, fee, inputs, outputs);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("type", type)
            .add("hash", hash)
            .add("size", size)
            .add("virtualSize", virtualSize)
            .add("version", version)
            .add("lockTime", lockTime)
            .add("blockNumber", blockNumber)
            .add("blockHash", blockHash)
            .add("blockTimestamp", blockTimestamp)
            .add("isCoinbase", isCoinbase)
            .add("inputCount", inputCount)
            .add("outputCount", outputCount)
            .add("inputValue", inputValue)
            .add("outputValue", outputValue)
            .add("fee", fee)
            .add("inputs", inputs)
            .add("outputs", outputs)
            .toString();
    }
}
