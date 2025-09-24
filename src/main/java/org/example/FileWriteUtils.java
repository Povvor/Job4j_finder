package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;

public class FileWriteUtils {

    public static void writeFile(List<Path> inputList, Path outputPath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath.toFile()))) {
            for (Path path : inputList) {
                writer.println(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
