package br.org.cesar.discordtime.stickysessions.injectors.modules

import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote
import br.org.cesar.discordtime.stickysessions.data.remote.repository.SessionRemoteRepository
import br.org.cesar.discordtime.stickysessions.data.remote.service.RemoteServiceFactory
import br.org.cesar.discordtime.stickysessions.data.remote.service.SessionService
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.Mapper
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.SessionMapper
import br.org.cesar.discordtime.stickysessions.domain.interactor.CreateSession
import br.org.cesar.discordtime.stickysessions.domain.interactor.EnterSession
import br.org.cesar.discordtime.stickysessions.domain.interactor.ListSessions
import br.org.cesar.discordtime.stickysessions.domain.interactor.RescheduleSession
import br.org.cesar.discordtime.stickysessions.domain.interactor.UseCase
import br.org.cesar.discordtime.stickysessions.domain.model.Session
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository
import br.org.cesar.discordtime.stickysessions.executor.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient


@Module
class TestSessionModule {

    @Provides
    fun provideSessionMapper(): Mapper<Session, SessionRemote> {
        return SessionMapper()
    }

    @Provides
    fun provideSessionService(url: String,
                              okHttpClient: OkHttpClient): SessionService {
        return RemoteServiceFactory<SessionService>()
                .makeRemoteService(url, SessionService::class.java, okHttpClient)
    }

    @Provides
    fun provideSessionRepository(
            sessionService: SessionService,
            mapper: Mapper<Session, SessionRemote>
    ): SessionRepository {
        return SessionRemoteRepository(
                sessionService,
                mapper
        )
    }

    @Provides
    fun provideCreateSessionUseCase(
            createSession: UseCase<SessionType, Session>
    ): IObservableUseCase<SessionType, Session> {
        return TestObservableUseCase(createSession)
    }

    @Provides
    fun provideEnterSessionUseCase(
            enterSession: UseCase<String, Session>): IObservableUseCase<String, Session> {

        return TestObservableUseCase(enterSession)
    }

    @Provides
    fun provideListUseCase(
            listSessions: UseCase<String, List<Session>>
    ): IObservableUseCase<String, List<Session>> {
        return TestObservableUseCase(listSessions)
    }

    @Provides
    fun provideRescheduleSessionUseCase(
            rescheduleSession: UseCase<Session, Session>
    ): IObservableUseCase<Session, Session> {
        return TestObservableUseCase(
                rescheduleSession
        )
    }

    @Provides
    fun provideCreateSession(sessionRepository: SessionRepository): UseCase<SessionType, Session> {
        return CreateSession(sessionRepository)
    }

    @Provides
    fun provideEnterSession(repository: SessionRepository): UseCase<String, Session> {
        return EnterSession(repository)
    }

    @Provides
    fun provideListSessions(repository: SessionRepository): UseCase<String, List<Session>> {
        return ListSessions(repository)
    }

    @Provides
    fun provideRescheduleSession(sessionRepository: SessionRepository): UseCase<Session, Session> {
        return RescheduleSession(sessionRepository)
    }
}
