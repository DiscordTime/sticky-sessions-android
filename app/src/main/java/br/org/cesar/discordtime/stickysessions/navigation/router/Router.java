package br.org.cesar.discordtime.stickysessions.navigation.router;

import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidRouteException;
import br.org.cesar.discordtime.stickysessions.navigation.repository.IRouteRepository;

public class Router implements IRouter {

    private IRouteRepository mRouteRepository;

    public Router(IRouteRepository routeRepository) {
        this.mRouteRepository = routeRepository;
    }

    @Override
    public Route getNext(String from, String event) throws InvalidRouteException {
        Route route = mRouteRepository.findRoute(from, event);
        if (route == null) {
            throw new InvalidRouteException(from, event);
        } else {
            return route;
        }
    }

}
