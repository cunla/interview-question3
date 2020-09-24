package com.example.demo.constants;


/**
 * Segregates External Messages used to complement response code of the response
 */
public final class ExternalMessage {

    public static final String RECORD_NOT_FOUND = "Record Not Found";

    public static final String FAILED_TO_CREATE_RECORD= "Failed to create a new record";

    private ExternalMessage(){
        throw new AssertionError();
    }

}
