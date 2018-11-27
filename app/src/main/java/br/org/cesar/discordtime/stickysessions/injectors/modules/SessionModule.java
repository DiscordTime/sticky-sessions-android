package br.org.cesar.discordtime.stickysessions.injectors.modules;

import android.content.Context;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote;
import br.org.cesar.discordtime.stickysessions.data.remote.repository.SessionRemoteRepository;
import br.org.cesar.discordtime.stickysessions.data.remote.service.RemoteServiceFactory;
import br.org.cesar.discordtime.stickysessions.data.remote.service.SessionService;
import br.org.cesar.discordtime.stickysessions.data.remote.wrapper.INetworkWrapper;
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.Mapper;
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.SessionMapper;
import br.org.cesar.discordtime.stickysessions.domain.interactor.CreateSession;
import br.org.cesar.discordtime.stickysessions.domain.interactor.EnterSession;
import br.org.cesar.discordtime.stickysessions.domain.interactor.ListSessions;
import br.org.cesar.discordtime.stickysessions.domain.interactor.UseCase;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType;
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository;
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase;
import br.org.cesar.discordtime.stickysessions.executor.ObservableUseCase;
import br.org.cesar.discordtime.stickysessions.executor.PostExecutionThread;
import br.org.cesar.discordtime.stickysessions.executor.ThreadExecutor;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;


@Module
public class SessionModule {

    @Provides
    public Mapper<Session, SessionRemote> provideSessionMapper() {
        return new SessionMapper();
    }
    
    @Provides
    public SessionService provideSessionService(Context context, String url,
                                                List<Interceptor> interceptors) {
        return new RemoteServiceFactory<SessionService>()
            .makeRemoteService(context, url, SessionService.class, interceptors);
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
    public IObservableUseCase<SessionType, Session> provideCreateSessionUseCase(
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
    public IObservableUseCase<String, Session> provideEnterSessionUseCase(
        EnterSession enterSession, ThreadExecutor threadExecutor,
        PostExecutionThread postExecutionThread) {

        return new ObservableUseCase<>(
            enterSession,
            threadExecutor,
            postExecutionThread);
    }

    @Provides
    public IObservableUseCase<Void, List<Session>> provideListUseCase(
        ListSessions listSessions,
        ThreadExecutor threadExecutor,
        PostExecutionThread postExecutionThread
    ) {
        return new ObservableUseCase<>(
            listSessions,
            threadExecutor,
            postExecutionThread
        );
    }

    @Provides
    public EnterSession provideEnterSession(SessionRepository repository) {
        return new EnterSession(repository);
    }

    @Provides
    public ListSessions provideListSessions(SessionRepository repository) {
        return new ListSessions(repository);
    }
}
