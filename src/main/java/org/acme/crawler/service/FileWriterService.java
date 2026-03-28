package org.acme.crawler.service;

import org.acme.crawler.model.PageResult;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class FileWriterService {

    private static final String OUTPUT_FILE = "crawler_results.txt";

    public void saveResult(String url, String title) {
        try (FileWriter fw = new FileWriter(OUTPUT_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(url + " -> " + title);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveResult(PageResult result) {
        if (result != null) {
            saveResult(result.getUrl(), result.getTitle());
        }
    }
}
