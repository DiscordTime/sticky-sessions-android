package br.org.cesar.discordtime.stickysessions.data.repository.mapper;

import br.org.cesar.discordtime.stickysessions.data.remote.model.NoteRemote;
import br.org.cesar.discordtime.stickysessions.domain.model.Note;

public class NoteMapper implements Mapper<Note, NoteRemote> {
    @Override
    public NoteRemote mapFromDomain(Note domainType) {
        NoteRemote remote = new NoteRemote();
        remote.sessionId = domainType.sessionId;
        remote.user = domainType.user;
        remote.topic = domainType.topic;
        remote.description = domainType.description;
        return remote;
    }

    @Override
    public Note mapToDomain(NoteRemote dataType) {
        return new Note(dataType.description, dataType.user, dataType.topic, dataType.sessionId);
    }
}
