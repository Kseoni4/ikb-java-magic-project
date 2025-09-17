package ru.mirea.hidden.tests;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

public class FilePathUtils {

    public static File getPackageDir(){
        String projectRoot = System.getProperty("user.dir");

        Path sourcePath = Paths.get(projectRoot, "src", "main", "java", "ru", "mirea", "magic");
        File packageDir = sourcePath.toFile();

        if (!packageDir.exists() || !packageDir.isDirectory()) {
            throw new RuntimeException("❌ Директория не найдена: " + sourcePath);
        }

        return packageDir;
    }

    public static File extractUserSubfolderDir(File[] packageDir) {
        Optional<File> first = Arrays.stream(packageDir)
                .filter(file -> file.isDirectory() && !file.getName().equals("task")
                        && !file.getName().equals("card")
                        && !file.getName().equals("gamelogic"))
                .findFirst();

        return first.orElse(null);
    }
}
