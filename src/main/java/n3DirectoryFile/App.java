package n3DirectoryFile;

import n2DirectoryTree.DirectoryTreePrinter;

import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        String directoryPath = "docs" + File.separator + "example_directory";
        String outputFile = "docs" + File.separator + "tree_output.txt";

        try {
            new DirectoryTreeToFile().saveTreeToFile(directoryPath, outputFile);
        } catch (IllegalArgumentException e) {
            System.err.println("[ERROR] Invalid argument: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("[ERROR] File operation failed: " + e.getMessage());
            System.exit(1);
        }

    }
}
