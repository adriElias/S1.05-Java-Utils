package n4FileReader;

import java.io.File;

public class App {

    public static void main(String[] args) {
        String txtFilePath   = "docs" + File.separator + "example_directory"
                + File.separator + "n1.txt";

        new TextFileReader().printFile(txtFilePath);
    }
}
