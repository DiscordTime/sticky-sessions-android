package br.org.cesar.discordtime.stickysessions.injectors.modules;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase;
import br.org.cesar.discordtime.stickysessions.logger.Logger;
import br.org.cesar.discordtime.stickysessions.navigation.router.IRouter;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundleFactory;
import br.org.cesar.discordtime.stickysessions.presentation.list.ListSessionsContract;
import br.org.cesar.discordtime.stickysessions.presentation.list.ListSessionsPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class ListSessionsModule {

    @Provides
    public ListSessionsContract.Presenter providePresenter(
        IObservableUseCase<String, List<Session>> listSessions,
        IObservableUseCase<Session, Session> rescheduleSession,
        IRouter router, Logger logger, IBundleFactory bundleFactory) {
        return new ListSessionsPresenter(listSessions, rescheduleSession,
                router, logger, bundleFactory);
    }
}
