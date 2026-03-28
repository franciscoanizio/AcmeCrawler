# 🕷️ Acme Crawler

Acme Crawler is a lightweight Java desktop application that concurrently fetches web pages, extracts their `<title>` tags using **jsoup**, displays the results in a **Swing** GUI, and saves them to a text file — all powered by **Java Virtual Threads**.

---

## ✨ Features

- 🔗 Crawl multiple URLs simultaneously using Java Virtual Threads (`Executors.newVirtualThreadPerTaskExecutor`)
- 🏷️ Extract page titles with **jsoup** HTML parsing
- 🖥️ Live results displayed in a Swing table
- 💾 Automatically saves crawled results to a text file via `FileWriterService`
- ⚡ Built with Java 26 and Maven

---

## 📦 Project Structure

```
src/main/java/org/acme/crawler/
├── App.java                        # Application entry point
├── controller/
│   └── CrawlerController.java      # Bridges UI actions to services
├── model/
│   └── PageResult.java             # Data model (URL + title)
├── service/
│   ├── CrawlerService.java         # Concurrent crawling logic
│   ├── ParserService.java          # HTML title extraction (jsoup)
│   └── FileWriterService.java      # Persists results to a text file
├── util/
│   └── HttpClientFactory.java      # Shared HttpClient factory
└── view/
    └── MainFrame.java              # Swing GUI (input + results table)
```

### Package Overview

| Package      | Responsibility                                                   |
|--------------|------------------------------------------------------------------|
| *(root)*     | `App.java` — launches the Swing application                      |
| `view`       | `MainFrame` — URL input area, results table, and crawl button    |
| `controller` | `CrawlerController` — delegates user actions to `CrawlerService` |
| `service`    | Crawling, HTML parsing, and file persistence logic               |
| `model`      | `PageResult` — holds a URL and its extracted title               |
| `util`       | `HttpClientFactory` — creates a shared `java.net.http.HttpClient`|

---

## ⚙️ Requirements

| Tool  | Version   |
|-------|-----------|
| Java  | 26 or later |
| Maven | 3.9+      |

> **Note:** An active internet connection is required both for fetching Maven dependencies and for crawling URLs at runtime.

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/franciscoanizio/AcmeCrawler.git
cd AcmeCrawler
```

### 2. Build the project

```bash
mvn clean install
```

### 3. Run the application

```bash
mvn exec:java
```

The Swing window will open automatically.

---

## 🖱️ How to Use

1. **Enter URLs** — type or paste one URL per line in the text area at the top of the window.
2. **Click "Iniciar Crawler"** — the application will fetch each URL concurrently.
3. **View results** — the table is updated in real time with each page's URL and extracted title.
4. **Check saved output** — results are also written to a local text file by `FileWriterService`.

---

## 🔧 Dependencies

| Library | Version | Purpose                  |
|---------|---------|--------------------------|
| [jsoup](https://jsoup.org/) | 1.17.2  | HTML parsing & title extraction |

All other features rely on the **Java Standard Library** (`java.net.http`, `javax.swing`, virtual threads).

---

## 🏗️ Architecture Overview

```
MainFrame (View)
    │  user action
    ▼
CrawlerController
    │  delegates to
    ▼
CrawlerService ──► HttpClientFactory ──► java.net.http.HttpClient
    │  response body
    ▼
ParserService (jsoup) ──► extracts <title>
    │
    ├──► SwingUtilities.invokeLater ──► updates JTable (MainFrame)
    └──► FileWriterService ──► appends result to output file
```
