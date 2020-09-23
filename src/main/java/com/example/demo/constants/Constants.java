package com.example.demo.constants;

public final class Constants {

    public static final String ERROR_MSG_TMPLT = "Method %s() - Exception Details: %s";

    public static final String ERROR_MSG_RECORD_NOT_FOUND_TMPLT = "Method %s() - Record not found for id %s";

    public static final String INFO_MSG_NO_PARAM_TMPLT = "Method %s() executed successfully. Results count %s";

    public static final String INFO_MSG_1_PARAM_TMPLT = "Method %s() for input %s executed successfully. Results count %s";

    public static final String INFO_MSG_CREATED_SUCCESS_TMPLT = "Method %s() for input %s executed successfully. Created new record with id %s";
    public static final String ERROR_MSG_CREATED_FAILED_TMPLT = "Method %s() for input %s failed. Exception Details: %s";

    public static final String INFO_MSG_FOUND_PARENT_RECORD = "Method %s() - found parent record for id  %s";

    private Constants(){
        throw new AssertionError();
    }

}
