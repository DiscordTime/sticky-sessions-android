package br.org.cesar.discordtime.stickysessions.injectors.modules

import br.org.cesar.discordtime.stickysessions.data.remote.repository.MeetingRemoteRepository
import br.org.cesar.discordtime.stickysessions.domain.interactor.ListMeetings
import br.org.cesar.discordtime.stickysessions.domain.interactor.UseCase
import br.org.cesar.discordtime.stickysessions.domain.model.Meeting
import br.org.cesar.discordtime.stickysessions.domain.repository.MeetingRepository
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository
import br.org.cesar.discordtime.stickysessions.executor.*
import dagger.Module
import dagger.Provides
import java.util.Comparator

@Module
class TestMeetingModule {

    @Provides
    fun provideMeetingRepository(sessionRepository: SessionRepository): MeetingRepository {
        return MeetingRemoteRepository(sessionRepository)
    }

    @Provides
    fun provideListMeetingsUseCase(
            listMeetings: UseCase<Comparator<Meeting>, List<Meeting>>
    ): IObservableUseCase<Comparator<Meeting>, List<Meeting>> {
        return TestObservableUseCase(listMeetings)
    }

    @Provides
    fun provideListMeetings(repository: MeetingRepository):
            UseCase<Comparator<Meeting>, MutableList<Meeting>> {
        return ListMeetings(repository)
    }
}