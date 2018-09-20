package br.org.cesar.discordtime.stickysessions.navigation.repository;

import java.util.HashMap;

import br.org.cesar.discordtime.stickysessions.navigation.router.IRouter;
import br.org.cesar.discordtime.stickysessions.navigation.router.Route;
import br.org.cesar.discordtime.stickysessions.ui.ViewNames;

public interface IRouteRepository {

    HashMap<String, Route> routesMap = new HashMap<String,Route>(){{
        put(
            ViewNames.LOBBY_ACTIVITY+IRouter.CREATED_SESSION,
            new Route(
                    ViewNames.LOBBY_ACTIVITY,
                    IRouter.CREATED_SESSION,
                    ViewNames.SESSION_ACTIVITY,
                    false)
        );
        put(
            ViewNames.LOBBY_ACTIVITY+IRouter.ENTERED_SESSION,
            new Route(
                    ViewNames.LOBBY_ACTIVITY,
                    IRouter.ENTERED_SESSION,
                    ViewNames.SESSION_ACTIVITY,
                    false)
        );
        put(
            ViewNames.LOBBY_ACTIVITY+IRouter.LISTED_SESSION,
            new Route(
                ViewNames.LOBBY_ACTIVITY,
                IRouter.LISTED_SESSION,
                ViewNames.LIST_ACTIVITY,
                false
            )
        );
    }};

    Route findRoute(String from, String event);
}
