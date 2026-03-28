package org.acme.crawler.model;

public class PageResult {
    private final String url;
    private final String title;

    public PageResult(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() { return url; }
    public String getTitle() { return title; }

    @Override
    public String toString() {
        return "URL: " + url + " | Tile: " + title;
    }
}