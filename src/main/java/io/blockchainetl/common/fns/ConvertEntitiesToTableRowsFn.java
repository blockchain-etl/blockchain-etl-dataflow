package io.blockchainetl.common.fns;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.common.utils.JsonUtils;
import io.blockchainetl.common.utils.TimeUtils;
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
    private Boolean addTimestampMonthColumn = true;


    public ConvertEntitiesToTableRowsFn(String startTimestamp, Long allowedTimestampSkewSeconds, String logPrefix,
        Boolean addTimestampMonthColumn) {
        this.startTimestamp = startTimestamp;
        this.allowedTimestampSkewSeconds = allowedTimestampSkewSeconds;
        this.logPrefix = logPrefix;
        this.addTimestampMonthColumn = addTimestampMonthColumn;
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
        if (addTimestampMonthColumn) {
            row.set(timestampFieldName + "_month", TimeUtils.formatDate(dateTime.withDayOfMonth(1)));
        }

        ZonedDateTime currentDateTime = ZonedDateTime.now();

        String entityId = getEntityId(element, jsonNode);

        if (this.allowedTimestampSkewSeconds != null
            && ChronoUnit.SECONDS.between(dateTime, currentDateTime) > this.allowedTimestampSkewSeconds) {
            LOG.error(logPrefix + String.format("Timestamp %s for entity %s of type %s exceeds the maximum allowed "
                    + "time skew.",
                dateTime, entityId, jsonNode.get("type")));
        } else if (this.startTimestamp != null && dateTime.isBefore(TimeUtils.parseDateTime(this.startTimestamp))) {
            LOG.debug(logPrefix + String.format("Timestamp %s for entity %s of type %s is before the startTimestamp.",
                dateTime, entityId, jsonNode.get("type")));
        } else {
            populateTableRowFields(row, element);
            LOG.info(logPrefix + String.format("Writing table row for entity %s of type %s.",
                entityId, jsonNode.get("type")));
            c.output(row);
        }
    }

    private String getEntityId(String element, JsonNode jsonNode) {
        String entityId = element;
        if (jsonNode.get("hash") != null) {
            entityId = jsonNode.get("hash").asText();
        } else if (jsonNode.get("transaction_hash") != null) {
            entityId = jsonNode.get("transaction_hash").asText();
        } else if (jsonNode.get("transaction_hash") != null) {
            entityId = jsonNode.get("transaction_hash").asText();
        } else if (jsonNode.get("address") != null) {
            entityId = jsonNode.get("address").asText();
        }
        return entityId;
    }

    protected abstract void populateTableRowFields(TableRow row, String element);
}
