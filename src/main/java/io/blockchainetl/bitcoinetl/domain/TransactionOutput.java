package io.blockchainetl.bitcoinetl.domain;

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
public class TransactionOutput {

    @Nullable
    private Long index;
    
    @Nullable
    @JsonProperty("script_asm")
    private String scriptAsm;

    @Nullable
    @JsonProperty("script_hex")
    private String scriptHex;

    @Nullable
    @JsonProperty("required_signatures")
    private Long requiredSignatures;

    @Nullable
    private String type;

    @Nullable
    private List<String> addresses;

    @Nullable
    private BigInteger value;

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getScriptAsm() {
        return scriptAsm;
    }

    public void setScriptAsm(String scriptAsm) {
        this.scriptAsm = scriptAsm;
    }

    public String getScriptHex() {
        return scriptHex;
    }

    public void setScriptHex(String scriptHex) {
        this.scriptHex = scriptHex;
    }

    public Long getRequiredSignatures() {
        return requiredSignatures;
    }

    public void setRequiredSignatures(Long requiredSignatures) {
        this.requiredSignatures = requiredSignatures;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransactionOutput that = (TransactionOutput) o;
        return Objects.equal(index, that.index) &&
            Objects.equal(scriptAsm, that.scriptAsm) &&
            Objects.equal(scriptHex, that.scriptHex) &&
            Objects.equal(requiredSignatures, that.requiredSignatures) &&
            Objects.equal(type, that.type) &&
            Objects.equal(addresses, that.addresses) &&
            Objects.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(index, scriptAsm, scriptHex, requiredSignatures, type, addresses, value);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("index", index)
            .add("scriptAsm", scriptAsm)
            .add("scriptHex", scriptHex)
            .add("requiredSignatures", requiredSignatures)
            .add("type", type)
            .add("addresses", addresses)
            .add("value", value)
            .toString();
    }
}
