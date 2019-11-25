package io.blockchainetl.eos.domain;

import com.google.common.base.Objects;
import org.apache.avro.reflect.Nullable;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@DefaultCoder(AvroCoder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {

    @Nullable
    private String type;

    @Nullable
    private String hash;

    @Nullable
    private String status;

    @Nullable
    @JsonProperty("cpu_usage_us")
    private Long cpuUsageUs;

    @Nullable
    @JsonProperty("net_usage_words")
    private Long netUsageWords;

    @Nullable
    private List<String> signatures;

    @Nullable
    private String compression;

    @Nullable
    @JsonProperty("packed_context_free_data")
    private String packedContextFreeData;

    @Nullable
    @JsonProperty("context_free_data")
    private List<String> contextFreeData;

    @Nullable
    @JsonProperty("packed_trx")
    private String packedTrx;

    @Nullable
    private String expiration;

    @Nullable
    @JsonProperty("ref_block_num")
    private String refBlockNum;

    @Nullable
    @JsonProperty("ref_block_prefix")
    private String refBlockPrefix;

    @Nullable
    @JsonProperty("max_net_usage_words")
    private Long maxNetUsageWords;

    @Nullable
    @JsonProperty("max_cpu_usage_ms")
    private Long maxCpuUsageMs;

    @Nullable
    @JsonProperty("delay_sec")
    private Long delaySec;

    @Nullable
    private Boolean deferred;

    @Nullable
    @JsonProperty("action_count")
    private Long actionCount;


    @Nullable
    @JsonProperty("block_number")
    private Long blockNumber;

    @Nullable
    @JsonProperty("block_hash")
    private String blockHash;

    @Nullable
    @JsonProperty("block_timestamp")
    private String blockTimestamp;

    public Transaction() {
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCpuUsageUs() {
        return cpuUsageUs;
    }

    public void setCpuUsageUs(Long cpuUsageUs) {
        this.cpuUsageUs = cpuUsageUs;
    }

    public Long getNetUsageWords() {
        return netUsageWords;
    }

    public void setNetUsageWords(Long netUsageWords) {
        this.netUsageWords = netUsageWords;
    }

    public List<String> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<String> signatures) {
        this.signatures = signatures;
    }

    public String getCompression() {
        return compression;
    }

    public void setCompression(String compression) {
        this.compression = compression;
    }

    public String getPackedContextFreeData() {
        return packedContextFreeData;
    }

    public void setPackedContextFreeData(String packedContextFreeData) {
        this.packedContextFreeData = packedContextFreeData;
    }

    public List<String> getContextFreeData() {
        return contextFreeData;
    }

    public void setContextFreeData(List<String> contextFreeData) {
        this.contextFreeData = contextFreeData;
    }

    public String getPackedTrx() {
        return packedTrx;
    }

    public void setPackedTrx(String packedTrx) {
        this.packedTrx = packedTrx;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getRefBlockNum() {
        return refBlockNum;
    }

    public void setRefBlockNum(String refBlockNum) {
        this.refBlockNum = refBlockNum;
    }

    public String getRefBlockPrefix() {
        return refBlockPrefix;
    }

    public void setRefBlockPrefix(String refBlockPrefix) {
        this.refBlockPrefix = refBlockPrefix;
    }

    public Long getMaxNetUsageWords() {
        return maxNetUsageWords;
    }

    public void setMaxNetUsageWords(Long maxNetUsageWords) {
        this.maxNetUsageWords = maxNetUsageWords;
    }

    public Long getMaxCpuUsageMs() {
        return maxCpuUsageMs;
    }

    public void setMaxCpuUsageMs(Long maxCpuUsageMs) {
        this.maxCpuUsageMs = maxCpuUsageMs;
    }

    public Long getDelaySec() {
        return delaySec;
    }

    public void setDelaySec(Long delaySec) {
        this.delaySec = delaySec;
    }

    public Boolean getDeferred() {
        return deferred;
    }

    public void setDeferred(Boolean deferred) {
        this.deferred = deferred;
    }

    public Long getActionCount() {
        return actionCount;
    }

    public void setActionCount(Long actionCount) {
        this.actionCount = actionCount;
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

    public String getBlockTimestamp() {
        return blockTimestamp;
    }

    public void setBlockTimestamp(String blockTimestamp) {
        this.blockTimestamp = blockTimestamp;
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
            Objects.equal(status, that.status) &&
            Objects.equal(cpuUsageUs, that.cpuUsageUs) &&
            Objects.equal(netUsageWords, that.netUsageWords) &&
            Objects.equal(signatures, that.signatures) &&
            Objects.equal(compression, that.compression) &&
            Objects.equal(packedContextFreeData, that.packedContextFreeData) &&
            Objects.equal(contextFreeData, that.contextFreeData) &&
            Objects.equal(packedTrx, that.packedTrx) &&
            Objects.equal(expiration, that.expiration) &&
            Objects.equal(refBlockNum, that.refBlockNum) &&
            Objects.equal(refBlockPrefix, that.refBlockPrefix) &&
            Objects.equal(maxNetUsageWords, that.maxNetUsageWords) &&
            Objects.equal(maxCpuUsageMs, that.maxCpuUsageMs) &&
            Objects.equal(delaySec, that.delaySec) &&
            Objects.equal(deferred, that.deferred) &&
            Objects.equal(actionCount, that.actionCount) &&
            Objects.equal(blockNumber, that.blockNumber) &&
            Objects.equal(blockHash, that.blockHash) &&
            Objects.equal(blockTimestamp, that.blockTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, hash, status, cpuUsageUs, netUsageWords, signatures, compression,
            packedContextFreeData, contextFreeData, packedTrx, expiration, refBlockNum, refBlockPrefix,
            maxNetUsageWords,
            maxCpuUsageMs, delaySec, deferred, actionCount, blockNumber, blockHash, blockTimestamp);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("type", type)
            .add("hash", hash)
            .add("status", status)
            .add("cpuUsageUs", cpuUsageUs)
            .add("net_usage_words", netUsageWords)
            .add("signatures", signatures)
            .add("compression", compression)
            .add("packedContextFreeData", packedContextFreeData)
            .add("contextFreeData", contextFreeData)
            .add("packedTrx", packedTrx)
            .add("expiration", expiration)
            .add("refBlockNum", refBlockNum)
            .add("refBlockPrefix", refBlockPrefix)
            .add("maxNetUsageWords", maxNetUsageWords)
            .add("maxCpuUsageMs", maxCpuUsageMs)
            .add("delaySec", delaySec)
            .add("deferred", deferred)
            .add("actionCount", actionCount)
            .add("blockNumber", blockNumber)
            .add("blockHash", blockHash)
            .add("blockTimestamp", blockTimestamp)
            .toString();
    }
}
