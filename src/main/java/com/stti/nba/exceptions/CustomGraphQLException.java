package com.stti.nba.exceptions;

public class CustomGraphQLException  extends RuntimeException {

    private final int statusCode;

    public CustomGraphQLException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
