package io.blockchainetl.bitcoinetl;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.SdkHarnessOptions;
import org.apache.beam.sdk.options.StreamingOptions;
import org.apache.beam.sdk.options.Validation;

public interface PubSubToBigQueryPipelineOptions extends PipelineOptions, StreamingOptions, SdkHarnessOptions {
    
    @Description("Prefix for PubSub subscription to read Dash data from")
    @Validation.Required
    String getDashPubSubSubscriptionPrefix();

    void setDashPubSubSubscriptionPrefix(String value);

    @Description("BigQuery dataset to write Dash data to")
    @Validation.Required
    String getDashBigQueryDataset();

    void setDashBigQueryDataset(String value);

    @Description("Start timestamp for streaming the data e.g. 2018-12-01T01:00:00Z")
    @Validation.Required
    String getDashStartTimestamp();

    void setDashStartTimestamp(String value);

    @Description("Timestamp skew for blocks and transactions, messages older than this will be rejected")
    Long getAllowedTimestampSkewSeconds();

    void setAllowedTimestampSkewSeconds(Long allowedTimestampSkewSeconds);
}
