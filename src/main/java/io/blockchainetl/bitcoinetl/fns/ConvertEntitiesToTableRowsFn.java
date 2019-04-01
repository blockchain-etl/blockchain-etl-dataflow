package io.blockchainetl.bitcoinetl.fns;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.bitcoinetl.utils.JsonUtils;
import io.blockchainetl.bitcoinetl.utils.TimeUtils;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public abstract class ConvertEntitiesToTableRowsFn extends ErrorHandlingDoFn<String, TableRow> {

    private static final Logger LOG = LoggerFactory.getLogger(ConvertEntitiesToTableRowsFn.class);

    private String startTimestamp;
    private Long allowedTimestampSkewSeconds;
    private String logPrefix = "";

    ConvertEntitiesToTableRowsFn(String startTimestamp, Long allowedTimestampSkewSeconds, String logPrefix) {
        this.startTimestamp = startTimestamp;
        this.allowedTimestampSkewSeconds = allowedTimestampSkewSeconds;
        this.logPrefix = logPrefix;
    }

    public ConvertEntitiesToTableRowsFn(String startTimestamp, Long allowedTimestampSkewSeconds) {
        this.startTimestamp = startTimestamp;
        this.allowedTimestampSkewSeconds = allowedTimestampSkewSeconds;
    }

    @Override
    protected void doProcessElement(ProcessContext c) throws IOException {
        String element = c.element();
        JsonNode jsonNode = JsonUtils.parseJson(element);

        String timestampFieldName;
        if (jsonNode.get("timestamp") != null) {
            timestampFieldName = "timestamp";
        } else if (jsonNode.get("block_timestamp") != null) {
            timestampFieldName = "block_timestamp";
        } else {
            LOG.error(logPrefix + "Element doesn't have timestamp field " + element);
            return;
        }

        TableRow row = new TableRow();

        long timestamp = jsonNode.get(timestampFieldName).getLongValue();
        ZonedDateTime dateTime = TimeUtils.convertToZonedDateTime(timestamp);
        row.set(timestampFieldName, TimeUtils.formatTimestamp(dateTime));
        row.set(timestampFieldName + "_month", TimeUtils.formatDate(dateTime.withDayOfMonth(1)));

        ZonedDateTime currentDateTime = ZonedDateTime.now();

        if (this.allowedTimestampSkewSeconds != null &&
            ChronoUnit.SECONDS.between(dateTime, currentDateTime) > this.allowedTimestampSkewSeconds
            ) {
            LOG.error(logPrefix + String.format("Timestamp %s for entity %s of type %s exceeds the maximum allowed time skew.",
                dateTime, jsonNode.get("hash"), jsonNode.get("type")));
        } else if (this.startTimestamp != null && dateTime.isBefore(TimeUtils.parseDateTime(this.startTimestamp))) {
            LOG.debug(logPrefix + String.format("Timestamp for entity %s fo type %s is before the startTimestamp.",
                jsonNode.get("hash"), jsonNode.get("type")));
        } else {
            populateTableRowFields(row, element);
            LOG.info(logPrefix + String.format("Writing table row for entity %s of type %s.",
                jsonNode.get("hash"), jsonNode.get("type")));
            c.output(row);
        }
    }

    protected abstract void populateTableRowFields(TableRow row, String element);
}