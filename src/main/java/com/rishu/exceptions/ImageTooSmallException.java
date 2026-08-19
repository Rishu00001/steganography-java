package com.rishu.exceptions;

public class ImageTooSmallException extends Exception {

    // Constructor jo custom error message accept karega
    public ImageTooSmallException(String message) {
        super(message);
    }
}