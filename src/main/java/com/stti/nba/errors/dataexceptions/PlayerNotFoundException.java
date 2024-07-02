package com.stti.nba.errors.dataexceptions;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(String message){
        super(message);
    }

    // @Override
    // public String getMessage() {
    //     return super.getMessage();
    // }

    // @Override
    // public ErrorClassification getErrorType() {
    //     return null;
    // }

    // @Override
    // public List<SourceLocation> getLocations() {
    //     return null;
    // }

    // @Override
    // public Map<String, Object> getExtensions() {
    //     return Collections.singletonMap("invalidFiled", invalidField);
    // }
}
