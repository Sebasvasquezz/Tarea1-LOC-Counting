package edu.escuelaing.arsw.ASE.app;

import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CountLinesTest {

    @Test
    public void testCountPhysicalLinesHelloWorld() {
        Path filePath = Paths.get("E:\\ARSW\\Tarea1\\LOC-Counting\\src\\main\\java\\edu\\escuelaing\\arsw\\ASE\\app\\HelloWorld.java");
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            int physicalLines = CountLines.countPhysicalLines(reader, filePath.getFileName().toString());
            assertEquals(16, physicalLines);
        } catch (IOException e) {
            fail("IOException thrown while counting lines: " + e.getMessage());
        }
    }

    @Test
    public void testCountLinesOfCodeHelloWorld() {
        Path filePath = Paths.get("E:\\ARSW\\Tarea1\\LOC-Counting\\src\\main\\java\\edu\\escuelaing\\arsw\\ASE\\app\\HelloWorld.java");
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            int linesOfCode = CountLines.countLinesOfCode(reader, filePath.getFileName().toString());
            assertEquals(6, linesOfCode);
        } catch (IOException e) {
            fail("IOException thrown while counting lines: " + e.getMessage());
        }
    }

    @Test
    public void testCountPhysicalLinesCalculator() {
        Path filePath = Paths.get("E:\\ARSW\\Tarea1\\LOC-Counting\\src\\main\\java\\edu\\escuelaing\\arsw\\ASE\\app\\Calculator.java");
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            int physicalLines = CountLines.countPhysicalLines(reader, filePath.getFileName().toString());
            assertEquals(52, physicalLines);
        } catch (IOException e) {
            fail("IOException thrown while counting lines: " + e.getMessage());
        }
    }

    @Test
    public void testCountLinesOfCodeCalculator() {
        Path filePath = Paths.get("E:\\ARSW\\Tarea1\\LOC-Counting\\src\\main\\java\\edu\\escuelaing\\arsw\\ASE\\app\\Calculator.java");
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            int linesOfCode = CountLines.countLinesOfCode(reader, filePath.getFileName().toString());
            assertEquals(18, linesOfCode);
        } catch (IOException e) {
            fail("IOException thrown while counting lines: " + e.getMessage());
        }
    }

        @Test
        public void testCountLinesInDirectory() {
            // Redirect standard output to a ByteArrayOutputStream to capture it
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            String directoryPath = "E:\\ARSW\\Tarea1\\LOC-Counting\\src\\main\\java\\edu\\escuelaing\\arsw\\ASE\\app";
    
            CountLines.main(new String[]{"loc", directoryPath});
            CountLines.main(new String[]{"phy", directoryPath});
    
            System.setOut(System.out);
    
            String output = outputStream.toString().trim();
    
            String[] lines = output.split("\n");
            assertEquals("Calculator.java: Lines of code: 18", lines[0].trim());
            assertEquals("CountLines.java: Lines of code: 98", lines[1].trim());
            assertEquals("HelloWorld.java: Lines of code: 6", lines[2].trim());
            assertEquals("Calculator.java: Physical lines: 52", lines[3].trim());
            assertEquals("CountLines.java: Physical lines: 142", lines[4].trim());
            assertEquals("HelloWorld.java: Physical lines: 16", lines[5].trim());
        }
    }