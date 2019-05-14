package br.org.cesar.discordtime.stickysessions.navigation.router;

import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidRouteException;

public interface IRouter {

    String USER_LOGGED = "user_logged";
    String CREATED_SESSION = "created_session";
    String ENTERED_SESSION = "entered_session";
    String LIST_SESSIONS = "list_sessions";
    String USER_SELECTED_SESSION = "user_selected_session";

    Route getNext(String from, String event) throws InvalidRouteException;

}
