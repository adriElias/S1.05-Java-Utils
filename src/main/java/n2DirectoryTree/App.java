package n2DirectoryTree;

import java.io.File;

public class App {

    public static void main(String[] args) {
        String directoryPath = "docs" + File.separator + "example_directory";
        DirectoryTreePrinter directoryTreePrinter = new DirectoryTreePrinter();
        System.out.println("Tree of: " + directoryPath);
        directoryTreePrinter.printTree(directoryPath, "");

    }
}
