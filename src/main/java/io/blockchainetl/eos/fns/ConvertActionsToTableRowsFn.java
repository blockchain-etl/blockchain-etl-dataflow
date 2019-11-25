package io.blockchainetl.eos.fns;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.common.fns.ConvertEntitiesToTableRowsFn;
import io.blockchainetl.common.fns.TimestampParserRepository;
import io.blockchainetl.common.utils.JsonUtils;
import io.blockchainetl.eos.domain.Action;
import io.blockchainetl.eos.domain.ActionAuthorization;
import io.blockchainetl.eos.domain.ActionData;

import java.util.ArrayList;
import java.util.List;

public class ConvertActionsToTableRowsFn extends ConvertEntitiesToTableRowsFn {

    public ConvertActionsToTableRowsFn(String startTimestamp, Long allowedTimestampSkewSeconds) {
        super(startTimestamp, allowedTimestampSkewSeconds, "", false,
            TimestampParserRepository.KEY_EOS);
    }

    public ConvertActionsToTableRowsFn(String startTimestamp, Long allowedTimestampSkewSeconds, String logPrefix) {
        super(startTimestamp, allowedTimestampSkewSeconds, logPrefix, false,
            TimestampParserRepository.KEY_EOS);
    }

    @Override
    protected void populateTableRowFields(TableRow row, String element) {
        Action action = JsonUtils.parseJson(element, Action.class);

        row.set("transaction_hash", action.getTransactionHash());
        row.set("account", action.getAccount());
        row.set("name", action.getName());
        if (action.getAuthorization() != null) {
            row.set("authorization", convertAuthorization(action.getAuthorization()));
        }
        if (action.getData() != null) {
            row.set("data", convertData(action.getData()));
        }
        row.set("hex_data", action.getHexData());

        row.set("block_number", action.getBlockNumber());
        row.set("block_hash", action.getBlockHash());
    }

    private List<TableRow> convertAuthorization(List<ActionAuthorization> authorization) {
        List<TableRow> result = new ArrayList<>();
        for (ActionAuthorization authorizationItem : authorization) {
            TableRow row = new TableRow()
                .set("actor", authorizationItem.getActor())
                .set("permission", authorizationItem.getPermission());
            result.add(row);
        }
        return result;
    }

    private List<TableRow> convertData(List<ActionData> data) {
        List<TableRow> result = new ArrayList<>();
        for (ActionData dataItem : data) {
            TableRow row = new TableRow()
                .set("key", dataItem.getKey())
                .set("value", dataItem.getValue());
            result.add(row);
        }
        return result;
    }
}
