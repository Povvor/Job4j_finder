package org.example;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) throws IOException {
        ArgsName argsName = ArgsName.of(args);
        Predicate<Path> predicate;
        predicate = PredicateGenerator.generatePredicate(argsName);

        List<Path> result = Search.search(Path.of(argsName.get("d")), predicate);

        FileWriteUtils.writeFile(result, Paths.get(argsName.get("o")));
    }
}