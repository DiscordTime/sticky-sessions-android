package br.org.cesar.discordtime.stickysessions.data.remote.repository;

import java.util.ArrayList;
import java.util.List;

import br.org.cesar.discordtime.stickysessions.BuildConfig;
import br.org.cesar.discordtime.stickysessions.data.remote.model.NoteRemote;
import br.org.cesar.discordtime.stickysessions.data.remote.service.NoteService;
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.Mapper;
import br.org.cesar.discordtime.stickysessions.domain.model.Note;
import br.org.cesar.discordtime.stickysessions.domain.model.NoteFilter;
import br.org.cesar.discordtime.stickysessions.domain.repository.NoteRepository;
import io.reactivex.Single;

public class NoteRemoteRepository implements NoteRepository {

    private NoteService mNoteService;
    private Mapper<Note, NoteRemote> mMapper;

    public NoteRemoteRepository(NoteService service,  Mapper<Note, NoteRemote> mapper) {
        mNoteService = service;
        mMapper = mapper;
    }

    @Override
    public Single<Note> addNote(Note note) {
        NoteRemote noteRemoteToAdd = mMapper.mapFromDomain(note);

        return mNoteService.addNote(noteRemoteToAdd).map(
            noteRemoteReturned -> mMapper.mapToDomain(noteRemoteReturned)
        );
    }

    @Override
    public Single<List<Note>> listNotesForSession(NoteFilter noteFilter) {
        return mNoteService.listNotesForSession(noteFilter.idSession).map(
            noteRemotes -> {
                List<Note> mNotes = new ArrayList<>();

                for (NoteRemote noteRemote : noteRemotes) {
                    mNotes.add(mMapper.mapToDomain(noteRemote));
                }

                return mNotes;
            }
        );
    }

    @Override
    public Single<Boolean> removeNote(Note note) {
        return  mNoteService.removeNote(note.id).map(
            responseBody -> true
        );
    }
}
