package org.example;

import java.util.HashMap;
import java.util.Map;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException("This key: '" + key + "' is missing");
        }
        return values.get(key);
    }

    public Map<String, String> getValues() {
        return values;
    }

    private void parse(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Arguments not passed to program");
        }
        for (String arg : args) {
            this.validateAndPut(arg);
        }
    }

    public static ArgsName of(String[] args) {
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    private void validateAndPut(String string) {
        if (!string.startsWith("-")) {
            throw new IllegalArgumentException("Error: This argument '" + string + "' does not start with a '-' character");
        }
        int firstEqualSign = string.indexOf('=');
        if (firstEqualSign == -1) {
            throw new IllegalArgumentException("Error: This argument '" + string + "' does not contain an equal sign");
        }
        String key = string.substring(1, firstEqualSign).trim();
        String value = string.substring(firstEqualSign + 1).trim();
        if (key.isEmpty() || key.charAt(0) == '-') {
            throw new IllegalArgumentException("Error: This argument '-=" + value + "' does not contain a key");
        }
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Error: This argument '-" + key + "=' does not contain a value");
        }
        values.put(key, value);
    }

    public static void main(String[] args) {
        ArgsName jvm = ArgsName.of(new String[] {"-Xmx=512", "-encoding=UTF-8"});
        System.out.println(jvm.get("Xmx"));

        ArgsName zip = ArgsName.of(new String[] {"-out=project.zip", "-encoding=UTF-8"});
        System.out.println(zip.get("out"));
    }
}