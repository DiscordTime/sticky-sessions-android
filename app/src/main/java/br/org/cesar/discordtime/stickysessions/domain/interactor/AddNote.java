package br.org.cesar.discordtime.stickysessions.domain.interactor;

import android.util.Log;

import javax.inject.Singleton;

import br.org.cesar.discordtime.stickysessions.domain.model.Note;
import br.org.cesar.discordtime.stickysessions.domain.repository.NoteRepository;
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository;
import io.reactivex.Single;

public class AddNote extends UseCase<Note, Note> {

    private NoteRepository mNoteRepository;
    private final SessionRepository mSessionRepository;

    public AddNote(NoteRepository noteRepository, SessionRepository sessionRepository) {
        Log.d("devlog", "create AddNote");
        mNoteRepository = noteRepository;
        mSessionRepository = sessionRepository;
    }

    @Override
    public Single<Note> execute(final Note note) {
        return mSessionRepository.getSession(note.sessionId).flatMap(session -> {
            if (session == null || session.id.isEmpty() || !session.id.equals(note.sessionId)) {
                return Single.error(
                        new IllegalArgumentException("The note's session is invalid.")
                );
            }
            else if (!session.topics.contains(note.topic)) {
                return Single.error(
                        new IllegalArgumentException("The note doesn't contain a topic of this " +
                                "session.")
                );
            }
            else if (note.description == null || note.description.trim().isEmpty()) {
                return Single.error(
                        new IllegalArgumentException("The note can't be empty.")
                );
            }

            return mNoteRepository.addNote(note);
        });
    }
}
