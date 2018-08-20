package br.org.cesar.discordtime.stickysessions.navigation.exception;

import br.org.cesar.discordtime.stickysessions.navigation.router.Route;

public class InvalidRouteException extends Exception {

    public InvalidRouteException(String from, String event){
        super(String.format("Invalid route: from:%1$s, event:%2$s", from, event));
    }

}
