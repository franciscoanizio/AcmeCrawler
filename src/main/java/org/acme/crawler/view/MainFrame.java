package org.acme.crawler.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import org.acme.crawler.controller.CrawlerController;

public class MainFrame extends JFrame {
    private DefaultTableModel tableModel;
    private JTextArea urlInput;
    private CrawlerController controller;

    public MainFrame() {
        super("Acme Crawler");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        urlInput = new JTextArea(5, 50);
        JScrollPane inputScroll = new JScrollPane(urlInput);

        tableModel = new DefaultTableModel(new Object[]{"URL", "Título"}, 0);
        JTable table = new JTable(tableModel);

        JButton crawlButton = new JButton("Iniciar Crawler");
        crawlButton.addActionListener(e -> {
            String[] urls = urlInput.getText().split("\\n");
            controller.startCrawl(urls);
        });

        add(inputScroll, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(crawlButton, BorderLayout.SOUTH);

        controller = new CrawlerController(tableModel);

        setVisible(true);
    }
}
