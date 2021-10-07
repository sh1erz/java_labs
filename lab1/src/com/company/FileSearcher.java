package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileSearcher implements Runnable {
    private final File dir;
    private final String extension = ".java";
    private final Path outputDir;
    private final ExecutorService pool;

    public FileSearcher(File dir, Path outputDir, ExecutorService pool) {
        this.dir = dir;
        this.outputDir = outputDir;
        this.pool = pool;
    }

    @Override
    public void run() {
        File[] files = dir.listFiles();
        List<Future<Boolean>> result = new ArrayList<>();
        for (File ff : files) {
            if (ff.isDirectory()) {
                FileSearcher searcher =
                        new FileSearcher(ff, outputDir, pool);
                result.add(pool.submit(searcher, true));
            } else if (hasExtension(ff)) {
                try {
                    String reformattedText = getReformattedString(ff);
                    writeToFile(reformattedText, ff.getName());
                } catch (IOException e) {
                    System.out.printf("Operation on file %s was not succeeded due to %s", ff.getName(), e.getMessage());
                    return;
                }
            }
        }
        for (Future<Boolean> rez : result) {
            try {
                rez.get();
            } catch (InterruptedException | ExecutionException e) {
                System.out.printf("Result of %s wasn't received - %s\n", rez, e.getMessage());
            }
        }
    }

    private void writeToFile(String text, String fileName) throws IOException {
        File file = new File(outputDir.toFile(), fileName);
        int index = 1;
        boolean isCreated = false;
        while (!isCreated) {
            if (file.createNewFile()) {
                isCreated = true;
            } else {
                file = new File(outputDir.toFile(), fileName.replace(extension, index++ + extension));
            }
        }
        try (BufferedWriter br = new BufferedWriter(new FileWriter(file))) {
            br.write(text);
        } catch (IOException ex) {
            System.out.printf("Error writing data to file %s due to %s", file.getName(), ex.getMessage());
        }
    }

    private boolean hasExtension(File file) {
        return file.getName().contains(extension);
    }

    private String getReformattedString(File file) throws IOException {
        Pattern multiComments = Pattern.compile("(?s)/\\*.*?\\*/", Pattern.MULTILINE);
        Pattern singleComment = Pattern.compile("//.*+");
        Pattern docComment = Pattern.compile("/\\*([\\S\\s]+?)\\*/", Pattern.MULTILINE);

        List<Pattern> patterns = new ArrayList<>();
        patterns.add(multiComments);
        patterns.add(singleComment);
        patterns.add(docComment);

        String data = Files.readAllLines(file.toPath()).stream().collect(Collectors.joining(System.lineSeparator()));
        for (Pattern pattern : patterns) {
            data = pattern.matcher(data).replaceAll("");
        }
        return data;
    }

}
