package org.acme.crawler.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import org.acme.crawler.controller.CrawlerController;

public class MainFrame extends JFrame {
    private final DefaultTableModel tableModel;
    private final JTextField urlInput;
    private final CrawlerController controller;

    public MainFrame() {
        super("Acme Crawler");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel: URL Input
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel urlLabel = new JLabel("URL:");
        urlInput = new JTextField(40);
        urlInput.setToolTipText("Enter a URL and click 'Add URL' to add it to the table");
        
        JButton addUrlButton = new JButton("Add URL");
        addUrlButton.addActionListener(_ -> addUrlToTable());
        
        inputPanel.add(urlLabel, BorderLayout.WEST);
        inputPanel.add(urlInput, BorderLayout.CENTER);
        inputPanel.add(addUrlButton, BorderLayout.EAST);

        // Center Panel: Table
        tableModel = new DefaultTableModel(new Object[]{"URL", "Title"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        JTable table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane tableScroll = new JScrollPane(table);

        // Bottom Panel: Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        
        JButton clearButton = new JButton("Clear Table");
        clearButton.addActionListener(_ -> clearTable());
        
        JButton crawlButton = new JButton("Start Crawler");
        crawlButton.addActionListener(_ -> startCrawl());

        buttonPanel.add(clearButton);
        buttonPanel.add(crawlButton);

        add(inputPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        controller = new CrawlerController(tableModel);

        setVisible(true);
    }

    private void addUrlToTable() {
        String url = urlInput.getText().trim();
        if (url.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a URL", "Empty URL", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!isValidUrl(url)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid URL", "Invalid URL", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        tableModel.addRow(new Object[]{url, ""});
        urlInput.setText("");
        urlInput.requestFocus();
    }

    private void clearTable() {
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }

    private void startCrawl() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Please add at least one URL", "No URLs", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String[] urls = new String[tableModel.getRowCount()];
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            urls[i] = (String) tableModel.getValueAt(i, 0);
        }
        
        controller.startCrawl(urls);
    }

    private boolean isValidUrl(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }
}
