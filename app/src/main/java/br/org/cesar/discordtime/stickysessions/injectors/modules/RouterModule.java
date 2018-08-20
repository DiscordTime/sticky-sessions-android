package br.org.cesar.discordtime.stickysessions.injectors.modules;

import br.org.cesar.discordtime.stickysessions.navigation.repository.IRouteRepository;
import br.org.cesar.discordtime.stickysessions.navigation.repository.RouteRepository;
import br.org.cesar.discordtime.stickysessions.navigation.router.IRouter;
import br.org.cesar.discordtime.stickysessions.navigation.router.Router;

import dagger.Module;
import dagger.Provides;

@Module
public class RouterModule {

    @Provides
    public IRouter providesRouter(IRouteRepository routeRepository){
        return new Router(routeRepository);
    }

    @Provides
    public IRouteRepository providesRouterRepository(){
        return new RouteRepository();
    }

}
