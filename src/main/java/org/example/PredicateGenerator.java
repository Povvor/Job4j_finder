package org.example;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.function.Predicate;

public class PredicateGenerator {
    public static Predicate<Path> generatePredicate(ArgsName argsName) {
        PathMatcher matcher;
        String arg = argsName.get("t");
        return switch (arg) {
            case "mask" -> {
                matcher = FileSystems.getDefault().getPathMatcher("glob:" + argsName.get("n"));
                yield path -> matcher.matches(path.getFileName());
            }
            case "name" -> path -> path.getFileName().toString().equals(argsName.get("n"));
            case "regex" -> {
                matcher = FileSystems.getDefault().getPathMatcher("regex:" + argsName.get("n"));
                yield path -> matcher.matches(path.getFileName());
            }
            default -> throw new IllegalArgumentException("This key: '" + argsName.get("t") + "' is missing");
        };
    }
}
