package n2DirectoryTree;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.stream.SystemOut;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SystemStubsExtension.class)
public class DirectoryTreePrinterTest {

    private static Path tempDir;
    private DirectoryTreePrinter printer;

    @SystemStub
    private SystemOut systemOut;

    @BeforeAll
    static void setupDir() throws IOException {
        tempDir = Files.createTempDirectory("tree_test");
        Files.createFile(tempDir.resolve("file_b.txt"));
        Files.createFile(tempDir.resolve("file_a.txt"));
        Path sub = Files.createDirectory(tempDir.resolve("subdir"));
        Files.createFile(sub.resolve("child.txt"));
    }

    @AfterAll
    static void tearDown() throws IOException {
        Files.walk(tempDir)
                .sorted((a, b) -> b.compareTo(a))
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @BeforeEach
    void setUp() {
        printer = new DirectoryTreePrinter();
    }

    @Test
    @DisplayName("Tree output contains all expected files and directories")
    void testTreeContainsAllEntries() {
        printer.printTree(tempDir.toString(), "");
        String output = systemOut.getText(); // ← obtiene el output directamente
        assertTrue(output.contains("file_a.txt"));
        assertTrue(output.contains("file_b.txt"));
        assertTrue(output.contains("subdir"));
        assertTrue(output.contains("child.txt"));
    }

    @Test
    @DisplayName("Tree marks directories with [D] and files with [F]")
    void testTreeMarksTypes() {
        printer.printTree(tempDir.toString(), "");
        String output = systemOut.getText();
        assertTrue(output.contains("[D] subdir"));
        assertTrue(output.contains("[F] file_a.txt"));
        assertTrue(output.contains("[F] file_b.txt"));
    }

}
