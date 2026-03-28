package org.acme.crawler.controller;

import javax.swing.table.DefaultTableModel;
import org.acme.crawler.service.CrawlerService;

public class CrawlerController {
    private final CrawlerService crawlerService;

    public CrawlerController(DefaultTableModel tableModel) {
        this.crawlerService = new CrawlerService(tableModel);
    }

    public void startCrawl(String[] urls) {
        crawlerService.startCrawl(urls);
    }
}