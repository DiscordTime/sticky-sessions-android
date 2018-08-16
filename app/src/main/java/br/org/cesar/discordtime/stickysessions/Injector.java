package br.org.cesar.discordtime.stickysessions;

import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote;
import br.org.cesar.discordtime.stickysessions.data.remote.repository.SessionRemoteRepository;
import br.org.cesar.discordtime.stickysessions.data.remote.service.RemoteServiceFactory;
import br.org.cesar.discordtime.stickysessions.data.remote.service.SessionService;
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.Mapper;
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.SessionMapper;
import br.org.cesar.discordtime.stickysessions.domain.interactor.CreateSession;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType;
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository;
import br.org.cesar.discordtime.stickysessions.executor.JobExecutor;
import br.org.cesar.discordtime.stickysessions.executor.MainThread;
import br.org.cesar.discordtime.stickysessions.executor.ObservableUseCase;
import br.org.cesar.discordtime.stickysessions.executor.PostExecutionThread;
import br.org.cesar.discordtime.stickysessions.executor.ThreadExecutor;

public class Injector {

    public static Mapper<Session, SessionRemote> provideSessionMapper() {
        return new SessionMapper();
    }

    public static SessionService provideSessionService() {
        return new RemoteServiceFactory<SessionService>()
                .makeRemoteService("http://localhost:3000", true, SessionService.class);
    }

    public static SessionRepository provideSessionRepository() {
        return new SessionRemoteRepository(
                provideSessionService(),
                provideSessionMapper()
        );
    }

    public static ObservableUseCase<SessionType, Session> provideCreateSessionUseCase() {
        return new ObservableUseCase<>(
                provideCreateSession(),
                provideThreadExecutor(),
                providePostExecutionThread()
        );
    }

    public static PostExecutionThread providePostExecutionThread() {
        return new MainThread();
    }

    public static ThreadExecutor provideThreadExecutor() {
        return new JobExecutor();
    }

    public static CreateSession provideCreateSession() {
        return  new CreateSession(provideSessionRepository());
    }

}
