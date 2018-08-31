package br.org.cesar.discordtime.stickysessions.domain.repository;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.domain.model.Note;
import br.org.cesar.discordtime.stickysessions.domain.model.NoteFilter;
import io.reactivex.Single;

public interface NoteRepository {
    Single<Note> addNote(Note note);
    Single<List<Note>> listNotesForSession(NoteFilter noteFilter);
}
