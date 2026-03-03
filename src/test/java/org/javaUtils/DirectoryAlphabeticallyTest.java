package org.javaUtils;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class DirectoryAlphabeticallyTest {
    
    private static Path tempDir;
    private DirectoryAlphabetically directoryAlphabetically;

    @BeforeAll
    static void setupDir() throws IOException {
        tempDir = Files.createTempDirectory("directoryAlphabetically_test");
        Files.createFile(tempDir.resolve("zebra.txt"));
        Files.createFile(tempDir.resolve("apple.txt"));
        Files.createFile(tempDir.resolve("mango.txt"));
        Files.createDirectory(tempDir.resolve("banana_dir"));
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
        directoryAlphabetically = new DirectoryAlphabetically();
    }

    @Test
    @DisplayName("Lists files in alphabetical order")
    void testAlphabeticalOrder() {
        File[] files = directoryAlphabetically.listAlphabetically(tempDir.toString());
        assertNotNull(files);
        assertEquals(4, files.length);
        assertEquals("apple.txt",  files[0].getName());
        assertEquals("banana_dir", files[1].getName());
        assertEquals("mango.txt",  files[2].getName());
        assertEquals("zebra.txt",  files[3].getName());
    }

    @Test
    @DisplayName("Returns empty array for non-existent directory")
    void testNonExistentDirectory() {
        File[] files = directoryAlphabetically.listAlphabetically("/non/existent/path");
        assertNotNull(files);
        assertEquals(0, files.length);
    }

    @Test
    @DisplayName("Returns empty array when path is a file, not a directory")
    void testFileInsteadOfDirectory() throws IOException {
        Path file = Files.createTempFile("not_a_dir", ".txt");
        File[] files = directoryAlphabetically.listAlphabetically(file.toString());
        assertEquals(0, files.length);
        Files.deleteIfExists(file);
    }

    @Test
    @DisplayName("Lists empty directory")
    void testEmptyDirectory() throws IOException {
        Path emptyDir = Files.createTempDirectory("empty_test");
        File[] files = directoryAlphabetically.listAlphabetically(emptyDir.toString());
        assertEquals(0, files.length);
        Files.delete(emptyDir);
    }

    @Test
    @DisplayName("Throws IllegalArgumentException for null path")
    void testNullPath() {
        assertThrows(IllegalArgumentException.class, () -> directoryAlphabetically.listAlphabetically(null));
    }

    @Test
    @DisplayName("Throws IllegalArgumentException for blank path")
    void testBlankPath() {
        assertThrows(IllegalArgumentException.class, () -> directoryAlphabetically.listAlphabetically("   "));
    }
}

