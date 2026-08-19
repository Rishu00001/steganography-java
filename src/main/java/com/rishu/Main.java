package com.rishu;

import com.rishu.models.SessionHistory;
import com.rishu.threads.ProcessTask;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SessionHistory sessionHistory = new SessionHistory();
        boolean running = true;

        System.out.println("==================================================");
        System.out.println("   STEGANOGRAPHY SECURE DATA COMMUNICATION APP    ");
        System.out.println("==================================================");

        while (running) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Encode (Hide a message in an image)");
            System.out.println("2. Decode (Extract a message from an image)");
            System.out.println("3. View Session History");
            System.out.println("4. Exit");
            System.out.print("Enter choice (1-4): ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    // ENCODE
                    System.out.print("Enter path of the COVER image (e.g., C:/images/car.png): ");
                    String coverPath = scanner.nextLine();
                    File coverImage = new File(coverPath);

                    System.out.print("Enter the SECRET message to hide: ");
                    String secretMessage = scanner.nextLine();

                    System.out.print("Enter path for the OUTPUT image (e.g., C:/images/car_secret.png): ");
                    String outputPath = scanner.nextLine();
                    File outputImage = new File(outputPath);

                    // Create task and start thread
                    ProcessTask encodeTask = new ProcessTask(coverImage, secretMessage, outputImage, sessionHistory);
                    Thread encodeThread = new Thread(encodeTask);
                    encodeThread.start();

                    // Wait for thread to finish before showing menu again
                    waitForThread(encodeThread);
                    break;

                case 2:
                    // DECODE
                    System.out.print("Enter path of the STEGO image (e.g., C:/images/car_secret.png): ");
                    String stegoPath = scanner.nextLine();
                    File stegoImage = new File(stegoPath);

                    // Create task and start thread
                    ProcessTask decodeTask = new ProcessTask(stegoImage, sessionHistory);
                    Thread decodeThread = new Thread(decodeTask);
                    decodeThread.start();

                    // Wait for thread to finish
                    waitForThread(decodeThread);
                    break;

                case 3:
                    // HISTORY
                    sessionHistory.displayHistory();
                    break;

                case 4:
                    // EXIT
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please select from 1 to 4.");
            }
        }
        scanner.close();
    }

    // Helper method to make the main thread wait for the background thread
    private static void waitForThread(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println("Process was interrupted.");
        }
    }
}