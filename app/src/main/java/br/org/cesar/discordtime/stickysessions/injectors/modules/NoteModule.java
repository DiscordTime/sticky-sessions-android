package br.org.cesar.discordtime.stickysessions.injectors.modules;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.data.remote.model.NoteRemote;
import br.org.cesar.discordtime.stickysessions.data.remote.repository.NoteRemoteRepository;
import br.org.cesar.discordtime.stickysessions.data.remote.service.NoteService;
import br.org.cesar.discordtime.stickysessions.data.remote.service.RemoteServiceFactory;
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.Mapper;
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.NoteMapper;
import br.org.cesar.discordtime.stickysessions.domain.interactor.AddNote;
import br.org.cesar.discordtime.stickysessions.domain.interactor.ListNotesForSession;
import br.org.cesar.discordtime.stickysessions.domain.interactor.RemoveNote;
import br.org.cesar.discordtime.stickysessions.domain.model.Note;
import br.org.cesar.discordtime.stickysessions.domain.model.NoteFilter;
import br.org.cesar.discordtime.stickysessions.domain.repository.NoteRepository;
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository;
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase;
import br.org.cesar.discordtime.stickysessions.executor.ObservableUseCase;
import br.org.cesar.discordtime.stickysessions.executor.PostExecutionThread;
import br.org.cesar.discordtime.stickysessions.executor.ThreadExecutor;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class NoteModule {

    @Provides
    public IObservableUseCase<Note, Note> provideObservableAddNoteUseCase(
        AddNote addNote, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new ObservableUseCase<>(
            addNote,
            threadExecutor,
            postExecutionThread
        );
    }

    @Provides
    public IObservableUseCase<NoteFilter, List<Note>> provideObservableListNotesUseCase(
        ListNotesForSession listNotesForSession, ThreadExecutor threadExecutor,
        PostExecutionThread postExecutionThread) {
        return new ObservableUseCase<>(
          listNotesForSession,
          threadExecutor,
          postExecutionThread
        );
    }

    @Provides
    public IObservableUseCase<Note, Boolean> provideObservableRemoveNoteUseCase(
        RemoveNote removeNote,
        ThreadExecutor threadExecutor,
        PostExecutionThread postExecutionThread) {
        return new ObservableUseCase<>(
            removeNote,
            threadExecutor,
            postExecutionThread
        );
    }

    @Provides
    public AddNote provideAddNote(NoteRepository noteRepository,
                                  SessionRepository sessionRepository) {
        return new AddNote(noteRepository, sessionRepository);
    }

    @Provides
    public RemoveNote provideRemoveNote(NoteRepository noteRepository) {
        return new RemoveNote(noteRepository);
    }

    @Provides
    public ListNotesForSession provideListNotes(NoteRepository noteRepository,
                                                SessionRepository sessionRepository) {
        return new ListNotesForSession(noteRepository, sessionRepository);
    }

    @Provides
    public NoteRepository provideNoteRepository(NoteService noteService,
                                                Mapper<Note, NoteRemote> mapper) {
        return new NoteRemoteRepository(noteService, mapper);
    }

    @Provides
    public NoteService provideNoteService(String baseUrl,
                                          OkHttpClient okHttpClient) {
        return new RemoteServiceFactory<NoteService>()
            .makeRemoteService(baseUrl, NoteService.class, okHttpClient);
    }

    @Provides
    public static Mapper<Note, NoteRemote> provideNoteMapper() {
        return new NoteMapper();
    }

}
