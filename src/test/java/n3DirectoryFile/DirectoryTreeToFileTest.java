package n3DirectoryFile;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DirectoryTreeToFileTest {
    private static Path tempDir;
    private static Path outputDir;
    private DirectoryTreeToFile directoryTreeToFile;

    @BeforeAll
    static void setupDir() throws IOException {
        tempDir = Files.createTempDirectory("tree_file_test");
        Files.createFile(tempDir.resolve("alpha.txt"));
        Files.createFile(tempDir.resolve("beta.txt"));
        Files.createDirectory(tempDir.resolve("subdir"));
        outputDir = Files.createTempDirectory("output_test");
    }

    @AfterAll
    static void tearDown() throws IOException {
        Files.walk(tempDir)
                .sorted((a, b) -> b.compareTo(a))
                .map(Path::toFile)
                .forEach(File::delete);
        Files.walk(outputDir)
                .sorted((a, b) -> b.compareTo(a))
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @BeforeEach
    void setUp() {
        directoryTreeToFile = new DirectoryTreeToFile();
    }

    @Test
    @DisplayName("Output TXT file is created")
    void testOutputFileCreated() throws IOException {
        String outputPath = outputDir.resolve("tree_output.txt").toString();
        directoryTreeToFile.saveTreeToFile(tempDir.toString(), outputPath);
        assertTrue(new File(outputPath).exists());
    }

    @Test
    @DisplayName("Output file contains expected file names")
    void testOutputFileContainsFileNames() throws IOException {
        String outputPath = outputDir.resolve("tree_content.txt").toString();
        directoryTreeToFile.saveTreeToFile(tempDir.toString(), outputPath);
        String content = new String(Files.readAllBytes(Paths.get(outputPath)));
        assertTrue(content.contains("alpha.txt"));
        assertTrue(content.contains("beta.txt"));
        assertTrue(content.contains("subdir"));
    }

    @Test
    @DisplayName("Output file contains [D] and [F] markers")
    void testOutputFileContainsTypeMarkers() throws IOException {
        String outputPath = outputDir.resolve("tree_markers.txt").toString();
        directoryTreeToFile.saveTreeToFile(tempDir.toString(), outputPath);
        String content = new String(Files.readAllBytes(Paths.get(outputPath)));
        assertTrue(content.contains("[F]"));
        assertTrue(content.contains("[D]"));
    }

    @Test
    @DisplayName("Source path is a file — writes [ERROR] message to output file")
    void testSourceIsFileWritesError() throws IOException {
        Path file = Files.createTempFile("not_a_dir", ".txt");
        String outputPath = outputDir.resolve("error_file.txt").toString();
        directoryTreeToFile.saveTreeToFile(file.toString(), outputPath);
        String content = new String(Files.readAllBytes(Paths.get(outputPath)));
        assertTrue(content.contains("[ERROR]"));
        Files.deleteIfExists(file);
    }
}
