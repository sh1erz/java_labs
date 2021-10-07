package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Path outputDir = createDirectory();
        File dir = getDirectory();
        System.out.println("Success " + dir.getPath());
        ExecutorService pool = Executors.newFixedThreadPool(10);
        Future<Boolean> future = pool.submit(new FileSearcher(dir, outputDir, pool), true);
        try {
            future.get();
            pool.shutdown();
            printAllFiles(outputDir);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void printAllFiles(Path dir) {
        File[] files = dir.toFile().listFiles();
        if (files == null) return;
        for (File file : files) {
            System.out.println("File: " + file + System.lineSeparator());
            try (Stream<String> lines = Files.lines(file.toPath())) {
                String data = lines.collect(Collectors.joining(System.lineSeparator()));
                System.out.println(data);
            } catch (IOException e) {
                System.out.println("Could not print file content due to " + e.getMessage());
            }
        }
    }

    private static File getDirectory() {
        while (true) {
            try (Scanner sc = new Scanner(System.in)) {
                while (true) {
                    System.out.println("Enter directory: ");
                    if (sc.hasNextLine()) {
                        File dir = new File(sc.nextLine());
                        if (dir.isDirectory()) {
                            return dir;
                        }
                        System.out.println("It is not a directory");
                    }
                }
            } catch (NullPointerException | SecurityException ex) {
                System.out.println("Error finding dir: " + ex.getMessage());
            }
        }
    }

    private static Path createDirectory() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter new directory name: ");
            if (sc.hasNextLine()) {
                try {
                    Path path = Paths.get(NEW_DIRECTORY_PATH, sc.nextLine());
                    if (Files.exists(path)) {
                        System.out.println("Files would be written in the existing directory");
                        return path;
                    }
                    System.out.println("New directory was created");
                    return Files.createDirectory(path);
                } catch (NullPointerException | SecurityException | IOException | UnsupportedOperationException |
                        InvalidPathException ex) {
                    System.out.println("Error creating dir: " + ex.getMessage());
                }
            }
        }
    }

    private static final String NEW_DIRECTORY_PATH = "C:\\Users\\Karina\\IdeaProjects\\java_lab1";
}
