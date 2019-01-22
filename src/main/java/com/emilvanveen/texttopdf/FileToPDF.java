package com.emilvanveen.texttopdf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

class FileToPDF {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            String[] inputs = {"tests/main.txt", "tests/blank-lines.txt", "tests/multiple-pages.txt", "tests/repeating-commands.txt", "tests/unknown-commands.txt", "tests/indent.txt"};
            String[] outputs = {"results/main.pdf", "results/blank-lines.pdf", "results/multiple-pages.pdf", "results/repeating-commands.pdf", "results/unknown-commands.pdf", "results/indent.pdf"};

            for (int i = 0; i < 6; i++) {
                FileToPDF.fileToPDF(inputs[i], outputs[i]);
            }
        } catch (Exception e) {
            System.out.println(String.format("E1: %s", e.toString()));
        }

    }

    /**
     *
     * @param inputPath the path to the file
     * @return List containing each line of the file
     * @throws IOException
     */
    private static void fileToPDF(String inputPath, String outputPath) throws IOException {
        try {
            /* Read every line and filter out the blank lines, as these can be ignored. */
            List<String> lines = Files.lines(Paths.get(inputPath))
                    .filter(s -> !s.equals(""))
                    .collect(Collectors.toList());

            ParseToPDF ftp = new ParseToPDF(lines);
            ftp.toPDF(outputPath);
        } catch (IOException e) {
            System.out.println(String.format("E2: %s", e.toString()));
        }
    }
}


