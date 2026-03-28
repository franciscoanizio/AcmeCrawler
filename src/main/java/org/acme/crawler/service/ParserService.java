package org.acme.crawler.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ParserService {
    public static String parseTitle(String html) {
        Document doc = Jsoup.parse(html);
        return doc.title();
    }
}
