package br.org.cesar.discordtime.stickysessions.navigation.exception;

public class InvalidViewNameException extends Exception {

    public InvalidViewNameException(String viewName) {
        super("Unmapped viewName: "+viewName);
    }
}
