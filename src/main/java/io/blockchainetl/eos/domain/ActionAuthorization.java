package io.blockchainetl.eos.domain;

import com.google.common.base.Objects;
import org.apache.avro.reflect.Nullable;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@DefaultCoder(AvroCoder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionAuthorization {

    @Nullable
    private String actor;

    @Nullable
    private String permission;
    
    public ActionAuthorization() {}

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActionAuthorization that = (ActionAuthorization) o;
        return Objects.equal(actor, that.actor) &&
            Objects.equal(permission, that.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(actor, permission);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("actor", actor)
            .add("permission", permission)
            .toString();
    }
}
