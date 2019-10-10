package br.org.cesar.discordtime.stickysessions.injectors.modules;

import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase;
import br.org.cesar.discordtime.stickysessions.logger.Logger;
import br.org.cesar.discordtime.stickysessions.presentation.session.SessionContract;
import br.org.cesar.discordtime.stickysessions.presentation.session.SessionPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class SessionPresenterModule {

    @Provides
    public SessionContract.Presenter providesPresenter(
        IObservableUseCase<String, Session> enterSession,
        IObservableUseCase<Void, String> getSavedUser,
        Logger logger){

        return new SessionPresenter(enterSession, getSavedUser, logger);
    }

}
