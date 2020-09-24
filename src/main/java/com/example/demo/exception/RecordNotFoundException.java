package com.example.demo.exception;

/**
 * Exception class to indicate that record was not found
 */
public class RecordNotFoundException extends RuntimeException {

    /**
     * CTOR to construct an instance from Message
     * @param message Message of the exception
     */
    public RecordNotFoundException(String message) {
        super(message);
    }
}
