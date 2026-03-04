package n4FileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TextFileReader {
    public String readFile(String filePath) throws IOException {
        validatePath(filePath);
        File file = new File(filePath);
        validateFile(file, filePath);
        return readContent(file);
    }

    public void printFile(String filePath) {
        try {
            String content = readFile(filePath);
            printContent(filePath, content);
        } catch (IllegalArgumentException e) {
            System.err.println("[ERROR] Invalid argument: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("[ERROR] Could not read file: " + e.getMessage());
        }
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

    private String readContent(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    private void printContent(String filePath, String content) {
        System.out.println("--- Content of: " + filePath + " ---");
        System.out.print(content);
        System.out.println("--- End of file ---");
    }
}
