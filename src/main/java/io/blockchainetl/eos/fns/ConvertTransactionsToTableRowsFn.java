package io.blockchainetl.eos.fns;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.common.fns.ConvertEntitiesToTableRowsFn;
import io.blockchainetl.common.fns.TimestampParserRepository;
import io.blockchainetl.common.utils.JsonUtils;
import io.blockchainetl.common.utils.TimeUtils;
import io.blockchainetl.eos.domain.Transaction;

import java.time.ZonedDateTime;

public class ConvertTransactionsToTableRowsFn extends ConvertEntitiesToTableRowsFn {

    public ConvertTransactionsToTableRowsFn(String startTimestamp, Long allowedTimestampSkewSeconds) {
        super(startTimestamp, allowedTimestampSkewSeconds, "", false,
            TimestampParserRepository.KEY_EOS);
    }

    public ConvertTransactionsToTableRowsFn(String startTimestamp, Long allowedTimestampSkewSeconds, String logPrefix) {
        super(startTimestamp, allowedTimestampSkewSeconds, logPrefix, false,
            TimestampParserRepository.KEY_EOS);
    }

    @Override
    protected void populateTableRowFields(TableRow row, String element) {
        Transaction transaction = JsonUtils.parseJson(element, Transaction.class);

        row.set("hash", transaction.getHash());
        row.set("status", transaction.getStatus());
        row.set("cpu_usage_us", transaction.getCpuUsageUs());
        row.set("net_usage_words", transaction.getNetUsageWords());
        row.set("signatures", transaction.getSignatures());
        row.set("compression", transaction.getCompression());
        row.set("packed_context_free_data", transaction.getPackedContextFreeData());
        row.set("context_free_data", transaction.getContextFreeData());
        row.set("packed_trx", transaction.getPackedTrx());

        if (transaction.getExpiration() != null) {
            ZonedDateTime dateTime = TimeUtils.convertToZonedDateTime(transaction.getExpiration(),
                EosConstants.DATE_TIME_FORMATTER);
            row.set("expiration", TimeUtils.formatTimestamp(dateTime));
        }

        row.set("ref_block_num", transaction.getRefBlockNum());
        row.set("ref_block_prefix", transaction.getRefBlockPrefix());
        row.set("max_net_usage_words", transaction.getMaxNetUsageWords());
        row.set("max_cpu_usage_ms", transaction.getMaxCpuUsageMs());
        row.set("delay_sec", transaction.getDelaySec());
        row.set("deferred", transaction.getDeferred());
        row.set("action_count", transaction.getActionCount());

        row.set("block_number", transaction.getBlockNumber());
        row.set("block_hash", transaction.getBlockHash());
    }
}
