package com.siteclearing.exception;

public class SimulatorException extends Exception {

    private final String message;

    public SimulatorException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
