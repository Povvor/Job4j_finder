package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) throws IOException {
        ArgsName argsName = ArgsName.of(args);
        Predicate<Path> predicate;
        PathMatcher matcher;
        predicate = switch (argsName.get("t")) {
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

        List<Path> result = Search.search(Path.of(argsName.get("d")), predicate);

        try (PrintWriter writer = new PrintWriter(new FileWriter(argsName.get("o")))) {
            for (Path path : result) {
                writer.println(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}