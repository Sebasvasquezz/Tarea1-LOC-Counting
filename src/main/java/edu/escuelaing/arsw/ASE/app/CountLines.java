package edu.escuelaing.arsw.ASE.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.regex.Pattern;
/**
This class provides functionalities to count physical and effective lines of code
in individual files or entire directories.
*/
public class CountLines {
    /**
     * Main method of the CountLines program.
     * It validates the input arguments and delegates the counting process.
     * @param args Command line arguments. Expects (phy|loc) (directory|file).
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Enter the parameters of the form (countType pathString)");
            return;
        }
        String countType = args[0];
        String pathString = args[1];
        Path path = Paths.get(pathString);

        try {
            if (Files.isDirectory(path)) {
                countLinesInDirectory(path, countType);
            } else {
                countLinesInFile(path, countType, path.getFileName().toString());
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Count the lines of code in the specified directory.
     * @param directory The directory to count lines in.
     * @param countType The type of count (phy or loc).
     * @throws IOException If an I/O error occurs.
     */
    public static void countLinesInDirectory(Path directory, String countType) throws IOException {
        FileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                countLinesInFile(file, countType, file.getFileName().toString());
                return FileVisitResult.CONTINUE;
            }
        };
        Files.walkFileTree(directory, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, fileVisitor);
    }

    /**
     * Count the lines of code in the specified file.
     * @param file The file to count lines in.
     * @param countType The type of count (phy or loc).
     * @param fileName The name of the file.
     * @return The number of lines counted.
     * @throws IOException If an I/O error occurs.
     */
    public static int countLinesInFile(Path file, String countType, String fileName) throws IOException {
        if (!Files.isRegularFile(file)) {
            return 0;
        }
        if (Pattern.matches(".*\\.java", fileName)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile()))) {
                switch (countType) {
                    case "phy":
                        return countPhysicalLines(reader, fileName);
                    case "loc":
                        return countLinesOfCode(reader, fileName);
                    default:
                        System.out.println("Unknown parameter: " + countType);
                        return 0;
                }
            }
        }
        return 0;
    }

    /**
     * Count the physical lines of code in the specified file.
     * @param reader The BufferedReader to read lines from the file.
     * @param fileName The name of the file.
     * @return The number of physical lines counted.
     * @throws IOException If an I/O error occurs.
     */
    public static int countPhysicalLines(BufferedReader reader, String fileName) throws IOException {
        int physicalLines = 0;
        while (reader.readLine() != null) {
            physicalLines++;
        }
        System.out.println(fileName + ": Physical lines: " + physicalLines);
        return physicalLines;
    }

    /**
     * Count the lines of code in the specified file, excluding comments and blank lines.
     * @param reader The BufferedReader to read lines from the file.
     * @param fileName The name of the file.
     * @return The number of lines of code counted.
     * @throws IOException If an I/O error occurs.
     */
    public static int countLinesOfCode(BufferedReader reader, String fileName) throws IOException {
        int linesOfCode = 0;
        boolean multiLineComment = false;

        String line;
        while ((line = reader.readLine()) != null) {
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty()) {
                continue;  // Skip blank lines
            }
            if (trimmedLine.startsWith("/*")) {
                multiLineComment = true;
                continue;  // Skip lines that start multi-line comments
            }
            if (multiLineComment) {
                if (trimmedLine.endsWith("*/")) {
                    multiLineComment = false;
                }
                continue;  // Skip lines inside multi-line comments
            }
            if (trimmedLine.startsWith("//")) {
                continue;  // Skip single-line comments
            }
            linesOfCode++;
        }
        System.out.println(fileName + ": Lines of code: " + linesOfCode);
        return linesOfCode;
    }
}
