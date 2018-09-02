package br.org.cesar.discordtime.stickysessions.injectors.modules;

import br.org.cesar.discordtime.stickysessions.navigation.repository.IRouteRepository;
import br.org.cesar.discordtime.stickysessions.navigation.repository.RouteRepository;
import br.org.cesar.discordtime.stickysessions.navigation.router.IRouter;
import br.org.cesar.discordtime.stickysessions.navigation.router.Router;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.BundleFactory;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundleFactory;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IViewStarter;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.ViewStarter;
import dagger.Module;
import dagger.Provides;

@Module
public class NavigationModule {

    @Provides
    public IRouter providesRouter(IRouteRepository routeRepository){
        return new Router(routeRepository);
    }

    @Provides
    public IRouteRepository providesRouterRepository(){
        return new RouteRepository();
    }

    @Provides
    public IViewStarter providesViewStarter(){
        return new ViewStarter();
    }

    @Provides
    public IBundleFactory providesBundleFactory() {
        return new BundleFactory();
    }
}
