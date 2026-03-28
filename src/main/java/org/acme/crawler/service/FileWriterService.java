package org.acme.crawler.service;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterService {
    private static final String FILE_NAME = "crawler_results.txt";

    public static synchronized void saveResult(String url, String title) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write("URL: " + url + " | Tile: " + title + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
