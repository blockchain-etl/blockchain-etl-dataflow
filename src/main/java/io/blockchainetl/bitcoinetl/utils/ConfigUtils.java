package io.blockchainetl.bitcoinetl.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Had to implement this as Dataflow doesn't support reading config from files.
 */
public class ConfigUtils {

    private static final String ARGS_FILE = "--argsFile";

    /**
     * Will read config from properties file in --argsFile option, overriding them with remaining args
     */
    public static String[] expandArgs(String[] args) throws IOException {
        Map<String, String> argsMap = parseArgs(args);
        if (argsMap.containsKey(ARGS_FILE)) {
            String argsFile = argsMap.get(ARGS_FILE);
            Map<String, String> argsFromFile = readConfigFromProperties(argsFile);

            argsFromFile.putAll(argsMap);
            argsFromFile.remove(ARGS_FILE);
            argsMap = argsFromFile;
        }

        List<String> result = argsMapToList(argsMap);

        return result.toArray(new String[result.size()]);
    }

    public static Map<String, String> parseArgs(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (String arg : args) {
            String[] parts = arg.split("=", 2);
            String part1 = parts[0]; 
            String part2 = null;
            if (parts.length > 1) {
                part2 = parts[1];
            }
            map.put(part1, part2);
        }

        return map;
    }
    
    private static Map<String, String> readConfigFromProperties(String file) throws IOException {
        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(file);
        properties.load(stream);

        Map<String, String> map = new HashMap<>();
        for (final String name: properties.stringPropertyNames()) {
            map.put(name, properties.getProperty(name));
        }
        
        return map;
    }
    
    private static List<String> argsMapToList(Map<String, String> argsMap) {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, String> entry : argsMap.entrySet()) {
            if (entry.getValue() != null) {
                result.add(entry.getKey() + "=" + entry.getValue());
            } else {
                result.add(entry.getKey());
            }
        }
        return result;
    }
}
