package br.org.cesar.discordtime.stickysessions.injectors.modules;

import android.content.Context;

import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote;
import br.org.cesar.discordtime.stickysessions.data.remote.repository.SessionRemoteRepository;
import br.org.cesar.discordtime.stickysessions.data.remote.service.RemoteServiceFactory;
import br.org.cesar.discordtime.stickysessions.data.remote.service.SessionService;
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.Mapper;
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.SessionMapper;
import br.org.cesar.discordtime.stickysessions.domain.interactor.CreateSession;
import br.org.cesar.discordtime.stickysessions.domain.interactor.EnterSession;
import br.org.cesar.discordtime.stickysessions.domain.interactor.UseCase;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType;
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository;
import br.org.cesar.discordtime.stickysessions.executor.ObservableUseCase;
import br.org.cesar.discordtime.stickysessions.executor.PostExecutionThread;
import br.org.cesar.discordtime.stickysessions.executor.ThreadExecutor;
import dagger.Module;
import dagger.Provides;


@Module
public class SessionModule {

    @Provides
    public Mapper<Session, SessionRemote> provideSessionMapper() {
        return new SessionMapper();
    }
    
    @Provides
    public SessionService provideSessionService(Context context, String url) {
        return new RemoteServiceFactory<SessionService>()
                .makeRemoteService(context, url, true, SessionService.class);
    }
    
    @Provides
    public SessionRepository provideSessionRepository(
            SessionService sessionService,
            Mapper<Session, SessionRemote> mapper
    ) {
        return new SessionRemoteRepository(
                sessionService,
                mapper
        );
    }

    @Provides
    public ObservableUseCase<SessionType, Session> provideCreateSessionUseCase(
            UseCase<SessionType, Session> createSession,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        return new ObservableUseCase<>(
                createSession,
                threadExecutor,
                postExecutionThread
        );
    }

    @Provides
    public UseCase<SessionType, Session> provideCreateSession(SessionRepository sessionRepository) {
        return new CreateSession(sessionRepository);
    }

    @Provides
    public ObservableUseCase<String, Session> provideEnterSessionUseCase(
        EnterSession enterSession, ThreadExecutor threadExecutor,
        PostExecutionThread postExecutionThread) {

        return new ObservableUseCase<>(
            enterSession,
            threadExecutor,
            postExecutionThread);
    }

    @Provides
    public EnterSession provideEnterSession(SessionRepository repository) {
        return new EnterSession(repository);
    }


}
