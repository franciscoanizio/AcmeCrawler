package org.acme.crawler.service;

import org.acme.crawler.model.PageResult;

import javax.swing.table.DefaultTableModel;

public class CrawlerService {

    private final FileWriterService fileWriterService;
    private final DefaultTableModel tableModel;

    public CrawlerService(DefaultTableModel tableModel) {
        this.fileWriterService = new FileWriterService();
        this.tableModel = tableModel;
    }

    public void startCrawl(String[] urls) {
        for (String url : urls) {
            // Aqui você implementaria a lógica real de crawling
            PageResult result = new PageResult(url, "Example Title");

            fileWriterService.saveResult(result);

            if (tableModel != null) {
                tableModel.addRow(new Object[]{result.getUrl(), result.getTitle()});
            }
        }
    }
}
