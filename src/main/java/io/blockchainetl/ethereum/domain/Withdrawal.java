package io.blockchainetl.ethereum.domain;

import com.google.common.base.Objects;
import org.apache.avro.reflect.Nullable;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.math.BigInteger;

@DefaultCoder(AvroCoder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Withdrawal {

    @Nullable
    private Long index;

    @Nullable
    @JsonProperty("validator_index")
    private Long validatorIndex;
    
    @Nullable
    private String address;

    @Nullable
    private BigInteger amount;
    
    public Withdrawal() {}

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Long getValidatorIndex() {
        return validatorIndex;
    }

    public void setValidatorIndex(Long validatorIndex) {
        this.validatorIndex = validatorIndex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Withdrawal that = (Withdrawal) o;
        return Objects.equal(index, that.index) &&
            Objects.equal(validatorIndex, that.validatorIndex) &&
            Objects.equal(address, that.address) &&
            Objects.equal(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(index, validatorIndex, address, amount);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("index", index)
            .add("validatorIndex", validatorIndex)
            .add("address", address)
            .add("amount", amount)
            .toString();
    }
}
