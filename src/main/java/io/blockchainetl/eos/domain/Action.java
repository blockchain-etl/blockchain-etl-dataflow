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
public class Action {

    @Nullable
    private String type;

    @Nullable
    @JsonProperty("transaction_hash")
    private String transactionHash;
    
    @Nullable
    private String account;

    @Nullable
    private String name;

    @Nullable
    private List<ActionAuthorization> authorization;

    @Nullable
    private List<ActionData> data;

    @Nullable
    @JsonProperty("hex_data")
    private String hexData;

    @Nullable
    @JsonProperty("block_timestamp")
    private String blockTimestamp;

    @Nullable
    @JsonProperty("block_number")
    private Long blockNumber;

    @Nullable
    @JsonProperty("block_hash")
    private String blockHash;
    
    public Action() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ActionAuthorization> getAuthorization() {
        return authorization;
    }

    public void setAuthorization(List<ActionAuthorization> authorization) {
        this.authorization = authorization;
    }

    public List<ActionData> getData() {
        return data;
    }

    public void setData(List<ActionData> data) {
        this.data = data;
    }

    public String getHexData() {
        return hexData;
    }

    public void setHexData(String hexData) {
        this.hexData = hexData;
    }

    public String getBlockTimestamp() {
        return blockTimestamp;
    }

    public void setBlockTimestamp(String blockTimestamp) {
        this.blockTimestamp = blockTimestamp;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Action action = (Action) o;
        return Objects.equal(type, action.type) &&
            Objects.equal(transactionHash, action.transactionHash) &&
            Objects.equal(account, action.account) &&
            Objects.equal(name, action.name) &&
            Objects.equal(authorization, action.authorization) &&
            Objects.equal(data, action.data) &&
            Objects.equal(hexData, action.hexData) &&
            Objects.equal(blockTimestamp, action.blockTimestamp) &&
            Objects.equal(blockNumber, action.blockNumber) &&
            Objects.equal(blockHash, action.blockHash);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, transactionHash, account, name, authorization, data, hexData, blockTimestamp,
            blockNumber, blockHash);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("type", type)
            .add("transactionHash", transactionHash)
            .add("account", account)
            .add("name", name)
            .add("authorization", authorization)
            .add("data", data)
            .add("hexData", hexData)
            .add("blockTimestamp", blockTimestamp)
            .add("blockNumber", blockNumber)
            .add("blockHash", blockHash)
            .toString();
    }
}
