package org.acme.crawler.util;

import java.net.http.HttpClient;

public class HttpClientFactory {
    public static HttpClient create() {
        return HttpClient.newHttpClient();
    }
}
