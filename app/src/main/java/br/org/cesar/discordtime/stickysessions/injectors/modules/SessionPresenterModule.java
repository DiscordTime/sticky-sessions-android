package br.org.cesar.discordtime.stickysessions.injectors.modules;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.domain.model.Note;
import br.org.cesar.discordtime.stickysessions.domain.model.NoteFilter;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase;
import br.org.cesar.discordtime.stickysessions.executor.ObservableUseCase;
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
        IObservableUseCase<Note, Note> addNote,
        IObservableUseCase<Note, Boolean> removeNote,
        IObservableUseCase<NoteFilter, List<Note>> listNotes,
        IObservableUseCase<Void, String> getSavedUser,
        Logger logger){

        return new SessionPresenter(enterSession, addNote, removeNote, listNotes,
            getSavedUser, logger);
    }

}
