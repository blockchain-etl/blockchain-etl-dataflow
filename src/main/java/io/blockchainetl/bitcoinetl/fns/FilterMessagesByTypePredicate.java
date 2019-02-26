package io.blockchainetl.bitcoinetl.fns;

import org.codehaus.jackson.JsonNode;

import static io.blockchainetl.bitcoinetl.utils.JsonUtils.parseJson;

public class FilterMessagesByTypePredicate extends ErrorHandlingPredicate<String> {

    private final String type;

    public FilterMessagesByTypePredicate(String type) {
        this.type = type;
    }

    @Override
    protected Boolean doApply(String msg) {
        JsonNode typeNode = parseJson(msg).get("type");
        return typeNode != null && typeNode.asText().equals(this.type);
    }
}
