package n5Serializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserializer {
    public Object deserialize(String filePath) throws IOException, ClassNotFoundException {
        validatePath(filePath);
        File file = new File(filePath);
        validateFile(file, filePath);
        return readObject(file);
    }

    private void validatePath(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path must not be null or blank.");
        }
    }

    private void validateFile(File file, String filePath) throws IOException {
        if (!file.exists()) {
            throw new IOException("File does not exist: " + filePath);
        }
        if (!file.isFile()) {
            throw new IOException("Path is not a regular file: " + filePath);
        }
        if (!file.canRead()) {
            throw new IOException("No read permission for file: " + filePath);
        }
    }

    private Object readObject(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return ois.readObject();
        }
    }
}
