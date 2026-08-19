package com.rishu.interfaces;

import com.rishu.exceptions.ImageTooSmallException;
import java.io.File;
import java.io.IOException;

public interface SteganoAlgorithm {
    // Ye method original image aur message lega, aur ek nayi
    // modified image save karega
    void encode(File coverImage,
                String secretMessage,
                File outputImage) throws IOException, ImageTooSmallException;

    // Ye method modified image lega aur usme se secret message nikal
    // kar return karega
    String decode(File stegoImage) throws IOException;
}
