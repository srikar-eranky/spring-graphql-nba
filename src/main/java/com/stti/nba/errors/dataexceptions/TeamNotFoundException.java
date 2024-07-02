package com.stti.nba.errors.dataexceptions;

public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException(String message) {
        super(message);
    }
}
