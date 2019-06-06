package br.org.cesar.discordtime.stickysessions.injectors.modules;

import java.util.Comparator;
import java.util.List;

import br.org.cesar.discordtime.stickysessions.data.remote.repository.MeetingRemoteRepository;
import br.org.cesar.discordtime.stickysessions.domain.interactor.ListMeetings;
import br.org.cesar.discordtime.stickysessions.domain.interactor.UseCase;
import br.org.cesar.discordtime.stickysessions.domain.model.Meeting;
import br.org.cesar.discordtime.stickysessions.domain.repository.MeetingRepository;
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository;
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase;
import br.org.cesar.discordtime.stickysessions.executor.ObservableUseCase;
import br.org.cesar.discordtime.stickysessions.executor.PostExecutionThread;
import br.org.cesar.discordtime.stickysessions.executor.ThreadExecutor;
import dagger.Module;
import dagger.Provides;

@Module
public class MeetingModule {

    @Provides
    public MeetingRepository provideMeetingRepository(SessionRepository sessionRepository) {
        return new MeetingRemoteRepository(sessionRepository);
    }

    @Provides
    public IObservableUseCase<Comparator<Meeting>, List<Meeting>> provideListMeetingsUseCase(
            UseCase<Comparator<Meeting>, List<Meeting>> listMeetings,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        return new ObservableUseCase<>(
                listMeetings,
                threadExecutor,
                postExecutionThread
        );
    }

    @Provides
    public UseCase<Comparator<Meeting>, List<Meeting>> provideListMeetings(MeetingRepository repository) {
        return new ListMeetings(repository);
    }
}
