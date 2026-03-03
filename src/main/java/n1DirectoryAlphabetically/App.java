package n1DirectoryAlphabetically;

import java.io.File;

public class App {

    public static void main(String[] args) {
        String directoryPath = "docs" + File.separator + "example_directory";
        new DirectoryAlphabetically().printDirectory(directoryPath);
    }
}
