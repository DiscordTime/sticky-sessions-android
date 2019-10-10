package br.org.cesar.discordtime.stickysessions.injectors.modules;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.domain.model.Note;
import br.org.cesar.discordtime.stickysessions.domain.model.NoteFilter;
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase;
import br.org.cesar.discordtime.stickysessions.presentation.notes.NotesContract;
import br.org.cesar.discordtime.stickysessions.presentation.notes.NotesPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class NoteTopicPresenterModule {
    @Provides
    public NotesContract.Presenter getNoteTopicPresenter(
            IObservableUseCase<Note, Note> addNote,
            IObservableUseCase<Note, Boolean> removeNote,
            IObservableUseCase<NoteFilter, List<Note>> listNotes) {
        return new NotesPresenter(listNotes, addNote, removeNote);
    }
}
