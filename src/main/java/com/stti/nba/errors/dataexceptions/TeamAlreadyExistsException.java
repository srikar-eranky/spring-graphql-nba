package com.stti.nba.errors.dataexceptions;

public class TeamAlreadyExistsException extends RuntimeException {

    public TeamAlreadyExistsException(String message) {
        super(message);
    }
    
}
