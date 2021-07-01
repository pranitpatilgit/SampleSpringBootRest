package com.pranitpatil.exception;

public class ApplicationException extends RuntimeException {

    private String userMessage;

    public ApplicationException(String userMessage) {
        super(userMessage);
        this.userMessage = userMessage;
    }

    public ApplicationException(Throwable cause, String userMessage) {
        super(cause);
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
