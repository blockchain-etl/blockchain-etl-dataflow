package io.blockchainetl.bitcoinetl.domain;

import com.google.common.base.Objects;
import org.apache.avro.reflect.Nullable;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@DefaultCoder(AvroCoder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Block {

    @Nullable
    private String type;
    
    @Nullable
    private String hash;
    
    @Nullable
    private Long size;
    
    @Nullable
    @JsonProperty("stripped_size")
    private Long strippedSize;
    
    @Nullable
    private Long weight;

    @Nullable
    private Long number;

    @Nullable
    private Long version;

    @Nullable
    @JsonProperty("merkle_root")
    private String merkleRoot;

    @JsonProperty("timestamp")
    private Long timestamp;

    @Nullable
    private String nonce;

    @Nullable
    private String bits;

    @Nullable
    @JsonProperty("coinbase_param")
    private String coinbaseParam;

    @JsonProperty("transaction_count")
    private Long transactionCount;

    public Block() {}

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

    public Long getStrippedSize() {
        return strippedSize;
    }

    public void setStrippedSize(Long strippedSize) {
        this.strippedSize = strippedSize;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getBits() {
        return bits;
    }

    public void setBits(String bits) {
        this.bits = bits;
    }

    public String getCoinbaseParam() {
        return coinbaseParam;
    }

    public void setCoinbaseParam(String coinbaseParam) {
        this.coinbaseParam = coinbaseParam;
    }

    public Long getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(Long transactionCount) {
        this.transactionCount = transactionCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Block block = (Block) o;
        return Objects.equal(type, block.type) &&
            Objects.equal(hash, block.hash) &&
            Objects.equal(size, block.size) &&
            Objects.equal(strippedSize, block.strippedSize) &&
            Objects.equal(weight, block.weight) &&
            Objects.equal(number, block.number) &&
            Objects.equal(version, block.version) &&
            Objects.equal(merkleRoot, block.merkleRoot) &&
            Objects.equal(timestamp, block.timestamp) &&
            Objects.equal(nonce, block.nonce) &&
            Objects.equal(bits, block.bits) &&
            Objects.equal(coinbaseParam, block.coinbaseParam) &&
            Objects.equal(transactionCount, block.transactionCount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, hash, size, strippedSize, weight, number, version, merkleRoot, timestamp, nonce,
            bits,
            coinbaseParam, transactionCount);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("type", type)
            .add("hash", hash)
            .add("size", size)
            .add("strippedSize", strippedSize)
            .add("weight", weight)
            .add("number", number)
            .add("version", version)
            .add("merkleRoot", merkleRoot)
            .add("timestamp", timestamp)
            .add("nonce", nonce)
            .add("bits", bits)
            .add("coinbaseParam", coinbaseParam)
            .add("transactionCount", transactionCount)
            .toString();
    }
}
