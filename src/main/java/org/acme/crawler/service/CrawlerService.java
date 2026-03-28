package org.acme.crawler.service;

import org.acme.crawler.model.PageResult;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class CrawlerService {

    private final FileWriterService fileWriterService;
    private final DefaultTableModel tableModel;
    private final HttpClient httpClient;

    public CrawlerService(DefaultTableModel tableModel) {
        this.fileWriterService = new FileWriterService();
        this.tableModel = tableModel;
        this.httpClient = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();
    }

    public void startCrawl(String[] urls) {
        for (int i = 0; i < urls.length; i++) {
            String url = urls[i];
            
            try {
                // Make HTTP request to get page content
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .header("User-Agent", "AcmeCrawler/1.0")
                    .build();
                
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                
                if (response.statusCode() == 200) {
                    // Extract title from HTML content
                    String title = ParserService.parseTitle(response.body());
                    
                    // Use extracted title or fallback to "No Title" if empty
                    String finalTitle = title != null && !title.trim().isEmpty() ? title : "No Title";
                    
                    PageResult result = new PageResult(url, finalTitle);
                    fileWriterService.saveResult(result);
                    
                    // Update table with extracted title
                    if (tableModel != null && i < tableModel.getRowCount()) {
                        tableModel.setValueAt(finalTitle, i, 1);
                    }
                } else {
                    // Handle non-200 responses
                    handleError(url, "HTTP " + response.statusCode(), i);
                }
                
            } catch (IOException e) {
                handleError(url, "Connection error: " + e.getMessage(), i);
            } catch (InterruptedException e) {
                handleError(url, "Request interrupted", i);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                handleError(url, "Error: " + e.getMessage(), i);
            }
        }
    }
    
    private void handleError(String url, String errorMessage, int rowIndex) {
        PageResult errorResult = new PageResult(url, "Error: " + errorMessage);
        fileWriterService.saveResult(errorResult);
        
        if (tableModel != null && rowIndex < tableModel.getRowCount()) {
            tableModel.setValueAt("Error: " + errorMessage, rowIndex, 1);
        }
    }
}
