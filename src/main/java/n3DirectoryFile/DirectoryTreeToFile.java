package n3DirectoryFile;

import n2DirectoryTree.DirectoryTreePrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DirectoryTreeToFile {
    private final DirectoryTreePrinter directoryTreePrinter = new DirectoryTreePrinter();

    public void saveTreeToFile(String dirPath, String outputFile) throws IOException {
        validateInputs(dirPath, outputFile);
        String tree = buildTreeContent(dirPath);

        writeToFile(outputFile, tree);
        System.out.println("Tree saved to: " + outputFile);
    }

    private void validateInputs(String dirPath, String outputFile) {
        if (dirPath == null || dirPath.isBlank()) {
            throw new IllegalArgumentException("Source directory path must not be null or blank.");
        }
        if (outputFile == null || outputFile.isBlank()) {
            throw new IllegalArgumentException("Output file path must not be null or blank.");
        }
    }

    private String buildTreeContent(String dirPath) {
        return "Tree of: " + dirPath + "\n" + directoryTreePrinter.buildTree(dirPath, "");
    }

    private void writeToFile(String outputFile, String content) throws IOException {
        File out = new File(outputFile);
        writeContent(out, content);
    }
    private void writeContent(File out, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(out))) {
            writer.write(content);
        }
    }

}
