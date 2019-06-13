package br.org.cesar.discordtime.stickysessions.injectors.modules

import br.org.cesar.discordtime.stickysessions.navigation.repository.IRouteRepository
import br.org.cesar.discordtime.stickysessions.navigation.repository.RouteRepository
import br.org.cesar.discordtime.stickysessions.navigation.router.IRouter
import br.org.cesar.discordtime.stickysessions.navigation.router.Router
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.BundleFactory
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundleFactory
import dagger.Module
import dagger.Provides

@Module
class TestNavigationModule {

    @Provides
    fun providesRouter(routeRepository: IRouteRepository): IRouter {
        return Router(routeRepository)
    }

    @Provides
    fun providesRouterRepository(): IRouteRepository {
        return RouteRepository()
    }

    @Provides
    fun providesBundleFactory(): IBundleFactory {
        return BundleFactory()
    }
}
