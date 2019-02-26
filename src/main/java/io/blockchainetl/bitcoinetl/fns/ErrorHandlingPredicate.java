package io.blockchainetl.bitcoinetl.fns;

import org.apache.beam.sdk.transforms.SerializableFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ErrorHandlingPredicate<F> implements SerializableFunction<F, Boolean> {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorHandlingPredicate.class);

    @Override
    public Boolean apply(F msg) {
        try {
            return doApply(msg);
        } catch (Exception e) {
            LOG.error("Failed to process input {}.", msg, e);
            return false;
        }
    }

    protected abstract Boolean doApply(F msg);
}
