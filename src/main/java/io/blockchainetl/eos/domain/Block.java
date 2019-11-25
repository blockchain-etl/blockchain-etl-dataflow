package io.blockchainetl.eos.domain;

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
    private Long number;

    @Nullable
    private String timestamp;

    @Nullable
    @JsonProperty("action_mroot")
    private String actionMroot;

    @Nullable
    @JsonProperty("transaction_mroot")
    private String transactionMroot;

    @Nullable
    private String producer;

    @Nullable
    @JsonProperty("transaction_count")
    private Long transactionCount;

    public Block() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getActionMroot() {
        return actionMroot;
    }

    public void setActionMroot(String actionMroot) {
        this.actionMroot = actionMroot;
    }

    public String getTransactionMroot() {
        return transactionMroot;
    }

    public void setTransactionMroot(String transactionMroot) {
        this.transactionMroot = transactionMroot;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
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
            Objects.equal(number, block.number) &&
            Objects.equal(timestamp, block.timestamp) &&
            Objects.equal(actionMroot, block.actionMroot) &&
            Objects.equal(transactionMroot, block.transactionMroot) &&
            Objects.equal(producer, block.producer) &&
            Objects.equal(transactionCount, block.transactionCount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, hash, number, timestamp, actionMroot, transactionMroot, producer,
            transactionCount);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("type", type)
            .add("hash", hash)
            .add("number", number)
            .add("timestamp", timestamp)
            .add("actionMroot", actionMroot)
            .add("transactionMroot", transactionMroot)
            .add("producer", producer)
            .add("transactionCount", transactionCount)
            .toString();
    }
}
