package br.org.cesar.discordtime.stickysessions.navigation.router;

import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidRouteException;

public interface IRouter {

    String CREATED_SESSION = "created_session";
    String ENTERED_SESSION = "entered_session";
    String LISTED_SESSION = "list_sessions";

    Route getNext(String from, String event) throws InvalidRouteException;

}
