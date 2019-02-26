package io.blockchainetl.bitcoinetl.utils;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonUtils {

    public static JsonNode parseJson(String msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseJson(String s, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(s, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
