package br.org.cesar.discordtime.stickysessions.injectors.modules;

import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType;
import br.org.cesar.discordtime.stickysessions.executor.ObservableUseCase;
import br.org.cesar.discordtime.stickysessions.navigation.router.IRouter;
import br.org.cesar.discordtime.stickysessions.presentation.lobby.LobbyContract;
import br.org.cesar.discordtime.stickysessions.presentation.lobby.LobbyPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class LobbyPresenterModule {

    @Provides
    public LobbyContract.Presenter providesPresenter(
            ObservableUseCase<SessionType, Session> createSession,
            IRouter router
    ){
        return new LobbyPresenter(createSession, router);
    }

}
