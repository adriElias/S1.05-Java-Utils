package n5Serializer;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectSerializerTest {
    private static Path tempDir;
    private static String serializedFilePath;
    private Serializer serializer;
    private Deserializer deserializer;
    private Person person;

    @BeforeAll
    static void setupDir() throws IOException {
        tempDir = Files.createTempDirectory("object_serializer_test");
        serializedFilePath = tempDir.resolve("person.ser").toString();
        new Serializer().serialize(new Person("Camila", "Villarroel", 32), serializedFilePath);
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
        serializer = new Serializer();
        deserializer = new Deserializer();
        person = new Person("Camila", "Villarroel", 32);
    }

    @Test
    @DisplayName("Person toString contains all fields")
    void testPersonToString() {
        String result = person.toString();
        assertTrue(result.contains("Camila"));
        assertTrue(result.contains("Villarroel"));
        assertTrue(result.contains("32"));
    }

    @Test
    @DisplayName(".ser file is created on disk after serialization")
    void testSerializeFileCreated() throws IOException {
        String path = tempDir.resolve("person.ser").toString();
        serializer.serialize(person, path);
        assertTrue(new File(path).exists());
    }

    @Test
    @DisplayName("Serializer creates parent directories if they do not exist")
    void testSerializeCreatesParentDirs() throws IOException {
        String path = tempDir.resolve("data" + File.separator + "person.ser").toString();
        serializer.serialize(person, path);
        assertTrue(new File(path).exists());
    }

    @Test
    @DisplayName("Serializer throws IllegalArgumentException for null object")
    void testSerializeNullObject() {
        assertThrows(IllegalArgumentException.class,
                () -> serializer.serialize(null, tempDir.resolve("x.ser").toString()));
    }

    @Test
    @DisplayName("Serializer throws IllegalArgumentException for null path")
    void testSerializeNullPath() {
        assertThrows(IllegalArgumentException.class, () -> serializer.serialize(person, null));
    }

    @Test
    @DisplayName("Serializer throws IllegalArgumentException for blank path")
    void testSerializeBlankPath() {
        assertThrows(IllegalArgumentException.class, () -> serializer.serialize(person, "  "));
    }

    @Test
    @DisplayName("Serializer throws IllegalArgumentException for non-Serializable object")
    void testSerializeNonSerializable() {
        Object notSerializable = new Object() {};
        assertThrows(IllegalArgumentException.class,
                () -> serializer.serialize(notSerializable, tempDir.resolve("invalid.ser").toString()));
    }

    @Test
    @DisplayName("Deserialized object equals the original")
    void testDeserializeRoundtrip() throws IOException, ClassNotFoundException {
        Person recovered = (Person) deserializer.deserialize(serializedFilePath);
        assertEquals(person, recovered);
    }

    @Test
    @DisplayName("Deserialized object has correct fields")
    void testDeserializedFields() throws IOException, ClassNotFoundException {
        Person recovered = (Person) deserializer.deserialize(serializedFilePath);
        assertEquals("Camila", recovered.getName());
        assertEquals("Villarroel", recovered.getSurname());
        assertEquals(32, recovered.getAge());
    }

    @Test
    @DisplayName("Deserializer throws IllegalArgumentException for null path")
    void testDeserializeNullPath() {
        assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize(null));
    }

    @Test
    @DisplayName("Deserializer throws IllegalArgumentException for blank path")
    void testDeserializeBlankPath() {
        assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize("  "));
    }

    @Test
    @DisplayName("Deserializer throws IOException for non-existent file")
    void testDeserializeNonExistentFile() {
        assertThrows(IOException.class, () -> deserializer.deserialize("/no/such/file.ser"));
    }

    @Test
    @DisplayName("Deserializer throws IOException when path is a directory")
    void testDeserializeDirectory() throws IOException {
        Path dir = Files.createTempDirectory("not_a_ser");
        assertThrows(IOException.class, () -> deserializer.deserialize(dir.toString()));
        Files.delete(dir);
    }
}

