package com.rishu.threads;

import com.rishu.core.LSBEncoder;
import com.rishu.exceptions.ImageTooSmallException;
import com.rishu.models.SessionHistory;

import java.io.File;
import java.io.IOException;

// Implementing Runnable to create a background thread
public class ProcessTask implements Runnable {

    private final LSBEncoder encoder;
    private final SessionHistory history;
    private final boolean isEncoding;

    // Variables for Encoding
    private File coverImage;
    private String secretMessage;
    private File outputImage;

    // Variables for Decoding
    private File stegoImage;

    // Constructor for Encoding Task
    public ProcessTask(File coverImage, String secretMessage, File outputImage, SessionHistory history) {
        this.isEncoding = true;
        this.encoder = new LSBEncoder();
        this.coverImage = coverImage;
        this.secretMessage = secretMessage;
        this.outputImage = outputImage;
        this.history = history;
    }

    // Constructor for Decoding Task
    public ProcessTask(File stegoImage, SessionHistory history) {
        this.isEncoding = false;
        this.encoder = new LSBEncoder();
        this.stegoImage = stegoImage;
        this.history = history;
    }

    @Override
    public void run() {
        System.out.println("\n[Thread-" + Thread.currentThread().getId() + "] Processing started... Please wait.");

        try {
            if (isEncoding) {
                encoder.encode(coverImage, secretMessage, outputImage);
                System.out.println("\n✅ Success! Message hidden in: " + outputImage.getAbsolutePath());
                history.addLog("Encode", coverImage.getName(), "SUCCESS");
            } else {
                String decodedMessage = encoder.decode(stegoImage);
                if (decodedMessage.isEmpty()) {
                    System.out.println("\n⚠️ No hidden message found in: " + stegoImage.getName());
                    history.addLog("Decode", stegoImage.getName(), "FAILED - No message");
                } else {
                    System.out.println("\n✅ Success! Hidden Message Extracted:");
                    System.out.println("--------------------------------------------------");
                    System.out.println(decodedMessage);
                    System.out.println("--------------------------------------------------");
                    history.addLog("Decode", stegoImage.getName(), "SUCCESS");
                }
            }
        } catch (IOException e) {
            System.out.println("\n❌ File Error: " + e.getMessage());
            history.addLog(isEncoding ? "Encode" : "Decode", "Unknown", "FAILED - IO Error");
        } catch (ImageTooSmallException e) {
            System.out.println("\n❌ Error: " + e.getMessage());
            history.addLog("Encode", coverImage.getName(), "FAILED - Image too small");
        }
    }
}
