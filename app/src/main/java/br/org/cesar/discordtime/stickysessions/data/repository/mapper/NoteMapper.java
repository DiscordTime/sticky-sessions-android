package br.org.cesar.discordtime.stickysessions.data.repository.mapper;

import org.jetbrains.annotations.NotNull;

import br.org.cesar.discordtime.stickysessions.data.remote.model.NoteRemote;
import br.org.cesar.discordtime.stickysessions.domain.model.Note;

public class NoteMapper implements Mapper<Note, NoteRemote> {
    @Override
    public NoteRemote mapFromDomain(@NotNull Note domainType) {
        NoteRemote remote = new NoteRemote();
        remote.sessionId = domainType.sessionId;
        remote.user = domainType.user;
        remote.topic = domainType.topic;
        remote.description = domainType.description;
        return remote;
    }

    @Override
    public Note mapToDomain(@NotNull NoteRemote dataType) {
        Note note = new Note();
        note.sessionId = dataType.sessionId;
        note.user = dataType.user;
        note.topic = dataType.topic;
        note.description = dataType.description;
        return note;
    }
}