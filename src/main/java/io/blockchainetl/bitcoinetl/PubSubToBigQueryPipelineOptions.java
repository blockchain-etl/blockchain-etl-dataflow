package io.blockchainetl.bitcoinetl;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.SdkHarnessOptions;
import org.apache.beam.sdk.options.StreamingOptions;
import org.apache.beam.sdk.options.Validation;

public interface PubSubToBigQueryPipelineOptions extends PipelineOptions, StreamingOptions, SdkHarnessOptions {
    
    @Description("PubSub subscription to read Dash data from")
    String getDashPubSubSubscription();

    void setDashPubSubSubscription(String value);

    @Description("BigQuery dataset to write Dash data to")
    String getDashBigQueryDataset();

    void setDashBigQueryDataset(String value);

    @Description("Start timestamp for streaming the data e.g. 2018-12-01T01:00:00Z")
    @Validation.Required
    String getDashStartTimestamp();

    void setDashStartTimestamp(String value);
}
