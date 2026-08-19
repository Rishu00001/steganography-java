package com.rishu.core;

import com.rishu.exceptions.ImageTooSmallException;
import com.rishu.interfaces.SteganoAlgorithm;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LSBEncoder extends BaseImageProcessor
        implements SteganoAlgorithm {

    // Ek secret code taaki decoder ko pata chale ki message kahan khatam hua
    private static final String DELIMITER = "###";

    @Override
    public void encode(File coverImage,
                       String secretMessage,
                       File outputImage) throws IOException,
            ImageTooSmallException {
        BufferedImage img = loadImage(coverImage);

        // Message ke end mein delimiter jod do
        String messageWithDelimiter = secretMessage + DELIMITER;
        byte[] msgBytes = messageWithDelimiter.getBytes();

        int totalBits = msgBytes.length * 8; // 1 byte = 8 bits

        // Exception Handling: Check if image has enough pixels
        if (totalBits > (img.getWidth() * img.getHeight())) {
            throw new ImageTooSmallException("Image is too small to hold this long message!");
        }

        int bitIndex = 0;

        // Loop through all pixels (x, y coordinates)
        outerLoop:
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                if (bitIndex < totalBits) {
                    int pixel = img.getRGB(x, y);

                    // Bitwise Operation: Extract current bit of our message
                    int byteIndex = bitIndex / 8;
                    int bitPosition = bitIndex % 8;
                    int bitValue = (msgBytes[byteIndex] >> (7 - bitPosition)) & 1;

                    // Modify the LSB (Least Significant Bit) of the pixel
                    if (bitValue == 1) {
                        pixel = pixel | 1; // Set last bit to 1
                    } else {
                        pixel = pixel & ~1; // Set last bit to 0
                    }

                    // Update pixel in the image
                    img.setRGB(x, y, pixel);
                    bitIndex++;
                } else {
                    break outerLoop; // All message bits are hidden!
                }
            }
        }
        // Save the modified stego-image using the method inherited from BaseImageProcessor
        saveImage(img, outputImage);
    }

    @Override
    public String decode(File stegoImage) throws IOException {
        BufferedImage img = loadImage(stegoImage);
        StringBuilder decodedMessage = new StringBuilder();
        int currentByte = 0;
        int bitCount = 0;

        // Loop through all pixels to extract hidden data
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = img.getRGB(x, y);
                int lsb = pixel & 1; // Extract the last bit

                // Build the byte bit-by-bit
                currentByte = (currentByte << 1) | lsb;
                bitCount++;

                if (bitCount == 8) {
                    decodedMessage.append((char) currentByte);
                    currentByte = 0;
                    bitCount = 0;

                    // Check if we hit our secret delimiter (end of message)
                    if (decodedMessage.toString().endsWith(DELIMITER)) {
                        return decodedMessage
                                .substring(0, decodedMessage.length()
                                        - DELIMITER.length());
                    }
                }
            }
        }
        return decodedMessage.toString();
    }
}
