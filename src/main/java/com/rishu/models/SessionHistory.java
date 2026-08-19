package com.rishu.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SessionHistory {

    // Using a List Collection to store operation logs
    private final List<String> operationLogs;
    private final DateTimeFormatter formatter;

    public SessionHistory() {
        this.operationLogs = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    // Add a new log entry
    public void addLog(String operation, String fileName, String status) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = String.format("[%s] %s on '%s' - Status: %s", timestamp, operation, fileName, status);
        operationLogs.add(logEntry);
    }

    // Display all logs
    public void displayHistory() {
        System.out.println("\n--- Session History ---");
        if (operationLogs.isEmpty()) {
            System.out.println("No operations performed in this session.");
        } else {
            for (String log : operationLogs) {
                System.out.println(log);
            }
        }
        System.out.println("-----------------------");
    }
}