package org.javaUtils;

import java.io.File;
import java.util.Arrays;

public class DirectoryAlphabetically {

    public File[] listAlphabetically(String path) {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("Path must not be null or blank.");
        }

        File dir = new File(path);

        if (!dir.exists()) {
            System.err.println("[ERROR] Path does not exist: " + path);
            return new File[0];
        }
        if (!dir.isDirectory()) {
            System.err.println("[ERROR] Path is not a directory: " + path);
            return new File[0];
        }
        if (!dir.canRead()) {
            System.err.println("[ERROR] No read permission for directory: " + path);
            return new File[0];
        }

        File[] files = dir.listFiles();

        if (files == null) {
            System.err.println("[ERROR] Could not list files (I/O error) in: " + path);
            return new File[0];
        }

        Arrays.sort(files, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        return files;

    }

    public void printDirectory(String path) {
        try {
            File[] files = listAlphabetically(path);
            if (files.length == 0) {
                System.out.println("Directory is empty or could not be read: " + path);
                return;
            }
            System.out.println("Contents of: " + path);
            for (File f : files) {
                System.out.println("  " + f.getName());
            }
        } catch (IllegalArgumentException e) {
            System.err.println("[ERROR] Invalid argument: " + e.getMessage());
        }
    }
}
