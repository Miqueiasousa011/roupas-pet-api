package br.com.roupaspet.services.exceptions;

public class APIException extends RuntimeException {
    public APIException(String message) {
        super(message);
    }
}
