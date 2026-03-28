package org.acme.crawler.controller;

import org.acme.crawler.model.PageResult;
import org.acme.crawler.service.CrawlerService;

import javax.swing.table.DefaultTableModel;

public class CrawlerController {

    private final CrawlerService crawlerService;
    private final DefaultTableModel tableModel;

    public CrawlerController(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
        this.crawlerService = new CrawlerService(tableModel);
    }

    public void startCrawl(String[] urls) {
        crawlerService.startCrawl(urls);
    }

    public void addResultToTable(PageResult result) {
        if (result != null) {
            tableModel.addRow(new Object[]{result.getUrl(), result.getTitle()});
        }
    }
}
