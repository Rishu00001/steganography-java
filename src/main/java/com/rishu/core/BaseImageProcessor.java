package com.rishu.core;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class BaseImageProcessor {

    // Method to read image from disk
    protected BufferedImage loadImage(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("File does not exist: "
                    + file.getAbsolutePath());
        }
        return ImageIO.read(file);
    }

    // Method to save the modified image back to disk
    protected void saveImage(BufferedImage image,
                             File file) throws IOException {
        // ALWAYS save as "png" for steganography.
        // Jpeg compression will destroy the hidden data.
        ImageIO.write(image, "png", file);
    }
}