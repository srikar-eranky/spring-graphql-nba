package com.stti.nba.errors.dataexceptions;

public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException(String invalidField) {
        super("invalid argument: " + invalidField);
    }
}
