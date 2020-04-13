package xyz.macromogic.testjudge.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static xyz.macromogic.testjudge.TestJudge.baseDirPath;

public class FileLoader {

    public static void load(String filePath, String destSubdirectory) throws IOException {
        File srcFile = new File(filePath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException(String.format("Source file `%s' does not exist.", filePath));
        }
        Files.createDirectories(Paths.get(destSubdirectory));
        Files.copy(Paths.get(filePath), Paths.get(baseDirPath + destSubdirectory + srcFile.getName()));
    }

    public static void load(String filePath) throws IOException {
        load(filePath, "");
    }

    public static void clear() {
        deleteDir(new File(baseDirPath), false);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void deleteDir(File baseDir, boolean deleteSelf) {
        File[] files = baseDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDir(file, true);
                } else {
                    file.delete();
                }
            }
        }
        if (deleteSelf) {
            baseDir.delete();
        }
    }
}
