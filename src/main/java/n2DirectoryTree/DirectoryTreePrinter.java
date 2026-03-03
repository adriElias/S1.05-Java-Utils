package n2DirectoryTree;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class DirectoryTreePrinter {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void printTree(String path, String indent) {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("Path must not be null or blank.");
        }
        File dir = new File(path);
        if (!isValidDirectory(dir, path)) return;

        File[] files = listSortedFiles(dir, path);
        if (files == null) return;

        printFiles(files, indent);
    }

    private boolean isValidDirectory(File dir, String path) {
        if (!dir.exists()) {
            System.err.println("[ERROR] Path does not exist: " + path);
            return false;
        }
        if (!dir.isDirectory()) {
            System.err.println("[ERROR] Path is not a directory: " + path);
            return false;
        }
        if (!dir.canRead()) {
            System.err.println("[ERROR] No read permission for: " + path);
            return false;
        }
        return true;
    }

    private File[] listSortedFiles(File dir, String path) {
        File[] files = dir.listFiles();
        if (files == null) {
            System.err.println("[ERROR] Could not list files (I/O error) in: " + path);
            return null;
        }
        Arrays.sort(files, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        return files;
    }

    private void printFiles(File[] files, String indent) {
        for (File f : files) {
            printEntry(f, indent);
            if (f.isDirectory()) {
                handleSubdirectory(f, indent);
            }
        }
    }

    private void printEntry(File f, String indent) {
        String type = f.isDirectory() ? "D" : "F";
        String lastModified = DATE_FORMAT.format(new Date(f.lastModified()));
        System.out.println(indent + "[" + type + "] " + f.getName() + " (" + lastModified + ")");
    }

    private void handleSubdirectory(File dir, String indent) {
        if (dir.canRead()) {
            printTree(dir.getAbsolutePath(), indent + "  ");
        } else {
            System.err.println(indent + "  [ERROR] No read permission for subdirectory: " + dir.getName());
        }
    }
}
