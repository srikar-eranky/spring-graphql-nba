package com.stti.nba.errors.dataexceptions;

public class PlayerAlreadyExistsException extends RuntimeException {

    public PlayerAlreadyExistsException(String message) {
        super(message);
    }
    
}
