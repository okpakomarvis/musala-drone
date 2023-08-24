package com.musala.drone.dronedispatchcontroller.exception;

public class ClientException extends RuntimeException{
    public ClientException(String message, Throwable cause) {
        super(message, cause);

    }
    public ClientException(String message) {
        super(message);

    }
    public ClientException(Throwable cause) {
        super(cause);

    }
}
