package org.acme.crawler.service;

import org.acme.crawler.model.PageResult;

/**
 * Service responsible for coordinating the crawling process.
 */
public class CrawlerService {

    private final FileWriterService fileWriterService;

    public CrawlerService() {
        this.fileWriterService = new FileWriterService();
    }

    /**
     * Process a crawled page result.
     *
     * @param pageResult the result containing URL and title
     */
    public void processResult(PageResult pageResult) {
        if (pageResult != null) {
            // Save result using the instance
            fileWriterService.saveResult(pageResult);
        }
    }

    // Other crawler logic (fetching, parsing, etc.) would go here
}
