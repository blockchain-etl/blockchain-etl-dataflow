package io.blockchainetl.eos.fns;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class EosConstants {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
        .ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS]").withZone(ZoneId.of("UTC"));
}
