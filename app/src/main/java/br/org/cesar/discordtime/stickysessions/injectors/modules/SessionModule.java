package br.org.cesar.discordtime.stickysessions.injectors.modules;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote;
import br.org.cesar.discordtime.stickysessions.data.remote.repository.SessionRemoteRepository;
import br.org.cesar.discordtime.stickysessions.data.remote.service.RemoteServiceFactory;
import br.org.cesar.discordtime.stickysessions.data.remote.service.SessionService;
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.Mapper;
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.SessionMapper;
import br.org.cesar.discordtime.stickysessions.domain.interactor.CreateSession;
import br.org.cesar.discordtime.stickysessions.domain.interactor.EnterSession;
import br.org.cesar.discordtime.stickysessions.domain.interactor.ListSessions;
import br.org.cesar.discordtime.stickysessions.domain.interactor.RescheduleSession;
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
import okhttp3.OkHttpClient;


@Module
public class SessionModule {

    @Provides
    public Mapper<Session, SessionRemote> provideSessionMapper() {
        return new SessionMapper();
    }

    @Provides
    public SessionService provideSessionService(String url,
                                                OkHttpClient okHttpClient) {
        return new RemoteServiceFactory<SessionService>()
            .makeRemoteService(url, SessionService.class, okHttpClient);
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
    public IObservableUseCase<String, Session> provideEnterSessionUseCase(
            UseCase<String, Session> enterSession,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {

        return new ObservableUseCase<>(
            enterSession,
            threadExecutor,
            postExecutionThread);
    }

    @Provides
    public IObservableUseCase<String, List<Session>> provideListUseCase(
            UseCase<String, List<Session>> listSessions,
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
    public IObservableUseCase<Session, Session> provideRescheduleSessionUseCase(
            UseCase<Session, Session> rescheduleSession,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        return new ObservableUseCase<>(
                rescheduleSession,
                threadExecutor,
                postExecutionThread
        );
    }

    @Provides
    public UseCase<SessionType, Session> provideCreateSession(SessionRepository sessionRepository) {
        return new CreateSession(sessionRepository);
    }

    @Provides
    public UseCase<String, Session> provideEnterSession(SessionRepository repository) {
        return new EnterSession(repository);
    }

    @Provides
    public UseCase<String, List<Session>> provideListSessions(SessionRepository repository) {
        return new ListSessions(repository);
    }

    @Provides
    public UseCase<Session, Session> provideRescheduleSession(SessionRepository sessionRepository) {
        return new RescheduleSession(sessionRepository);
    }
}
