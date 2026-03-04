package n4FileReader;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class TextFileReaderTest {
    private static Path tempFile;
    private static Path tempDir;
    private TextFileReader reader;

    @BeforeAll
    static void setup() throws IOException {
        tempFile = Files.createTempFile("reader_test", ".txt");
        Files.writeString(tempFile, "Hello World\nTalent Arena\n4YFN\nMWC\n2026");
        tempDir = Files.createTempDirectory("reader_dir_test");
        Files.createFile(tempDir.resolve("alpha.txt"));
        Files.createFile(tempDir.resolve("beta.txt"));
    }

    @AfterAll
    static void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
        Files.walk(tempDir)
                .sorted((a, b) -> b.compareTo(a))
                .map(Path::toFile)
                .forEach(java.io.File::delete);
    }

    @BeforeEach
    void setUp() {
        reader = new TextFileReader();
    }

    @Test
    @DisplayName("Reads content of an existing TXT file")
    void testReadsContent() throws IOException {
        String content = reader.readFile(tempFile.toString());
        assertTrue(content.contains("Hello World"));
        assertTrue(content.contains("Talent Arena"));
        assertTrue(content.contains("4YFN"));
        assertTrue(content.contains("MWC"));
    }

    @Test
    @DisplayName("Reads empty file without errors")
    void testEmptyFile() throws IOException {
        Path emptyFile = Files.createTempFile("empty", ".txt");
        String content = reader.readFile(emptyFile.toString());
        assertNotNull(content);
        assertEquals("", content.trim());
        Files.delete(emptyFile);
    }

    @Test
    @DisplayName("Returns full content including all lines")
    void testLineCount() throws IOException {
        String content = reader.readFile(tempFile.toString());
        String[] lines = content.split(System.lineSeparator());
        assertEquals(5, lines.length);
    }
}
