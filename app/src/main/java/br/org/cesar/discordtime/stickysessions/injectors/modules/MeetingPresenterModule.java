package br.org.cesar.discordtime.stickysessions.injectors.modules;

import java.util.Comparator;
import java.util.List;

import br.org.cesar.discordtime.stickysessions.domain.model.Meeting;
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase;
import br.org.cesar.discordtime.stickysessions.presentation.meeting.MeetingContract;
import br.org.cesar.discordtime.stickysessions.presentation.meeting.MeetingPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class MeetingPresenterModule {

    @Provides
    public MeetingContract.Presenter providesMeetingPresenter(
            IObservableUseCase<Comparator<Meeting>, List<Meeting>> listMeetings
    ) {
        return new MeetingPresenter(listMeetings);
    }
}
