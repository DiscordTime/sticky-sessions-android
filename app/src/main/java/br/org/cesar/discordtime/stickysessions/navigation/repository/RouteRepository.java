package br.org.cesar.discordtime.stickysessions.navigation.repository;

import br.org.cesar.discordtime.stickysessions.navigation.router.Route;

public class RouteRepository implements IRouteRepository {

    @Override
    public Route findRoute(String from, String event) {
        if (from == null || event == null) {
            throw new IllegalArgumentException("From and Event arguments must not be null");
        }

        return routesMap.get(from+event);
    }
}
