package br.org.cesar.discordtime.stickysessions.data.remote.repository;

import br.org.cesar.discordtime.stickysessions.data.remote.model.NoteRemote;
import br.org.cesar.discordtime.stickysessions.data.remote.service.NoteService;
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.Mapper;
import br.org.cesar.discordtime.stickysessions.domain.model.Note;
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
}
