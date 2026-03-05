package n5Serializer;

import java.io.*;

public class Serializer {
    public void serialize(Object obj, String filePath) throws IOException {
        validateSerializeInputs(obj, filePath);
        File file = new File(filePath);
        ensureParentDirectoriesExist(file, filePath);
        validateWritePermission(file, filePath);
        writeObject(obj, file);
        System.out.println("Object serialized to: " + filePath);
    }

    private void validateSerializeInputs(Object obj, String filePath) {
        if (obj == null) {
            throw new IllegalArgumentException("Object to serialize must not be null.");
        }
        validatePath(filePath);
        if (!(obj instanceof Serializable)) {
            throw new IllegalArgumentException(
                    "Object of type " + obj.getClass().getName() + " does not implement Serializable.");
        }
    }

    private void validatePath(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path must not be null or blank.");
        }
    }

    private void ensureParentDirectoriesExist(File file, String filePath) throws IOException {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            if (!parent.mkdirs()) {
                throw new IOException("Could not create parent directories for: " + filePath);
            }
        }
    }

    private void validateWritePermission(File file, String filePath) throws IOException {
        if (file.exists() && !file.canWrite()) {
            throw new IOException("No write permission for file: " + filePath);
        }
    }

    private void writeObject(Object obj, File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(obj);
        }
    }

}
