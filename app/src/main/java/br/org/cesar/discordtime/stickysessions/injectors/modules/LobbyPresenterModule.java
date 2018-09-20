package br.org.cesar.discordtime.stickysessions.injectors.modules;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType;
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase;
import br.org.cesar.discordtime.stickysessions.logger.Logger;
import br.org.cesar.discordtime.stickysessions.navigation.router.IRouter;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundleFactory;
import br.org.cesar.discordtime.stickysessions.presentation.lobby.LobbyContract;
import br.org.cesar.discordtime.stickysessions.presentation.lobby.LobbyPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class LobbyPresenterModule {

    @Provides
    public LobbyContract.Presenter providesPresenter(
            IObservableUseCase<SessionType, Session> createSession,
            IRouter router,
            Logger logger,
            IBundleFactory bundleFactory
    ){
        return new LobbyPresenter(createSession, router, logger, bundleFactory);
    }

}
