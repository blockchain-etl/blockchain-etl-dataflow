package io.blockchainetl.eos.domain;

import com.google.common.base.Objects;
import org.apache.avro.reflect.Nullable;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@DefaultCoder(AvroCoder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionData {

    @Nullable
    private String key;

    @Nullable
    private String value;
    
    public ActionData() {}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
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
        ActionData that = (ActionData) o;
        return Objects.equal(key, that.key) &&
            Objects.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key, value);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("key", key)
            .add("value", value)
            .toString();
    }
}
