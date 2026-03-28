package org.acme.crawler.service;

import javax.swing.table.DefaultTableModel;
import java.net.http.*;
import java.net.URI;
import java.util.concurrent.Executors;
import org.acme.crawler.model.PageResult;
import org.acme.crawler.util.HttpClientFactory;

public class CrawlerService {
    private final DefaultTableModel tableModel;

    public CrawlerService(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public void startCrawl(String[] urls) {
        HttpClient client = HttpClientFactory.create();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (String url : urls) {
                executor.submit(() -> {
                    try {
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(url.trim()))
                                .build();
                        HttpResponse<String> response = client.send(request,
                                HttpResponse.BodyHandlers.ofString());

                        String title = ParserService.parseTitle(response.body());
                        PageResult result = new PageResult(url, title);

                        javax.swing.SwingUtilities.invokeLater(() ->
                                tableModel.addRow(new Object[]{result.getUrl(), result.getTitle()})
                        );

                        FileWriterService.saveResult(result);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
