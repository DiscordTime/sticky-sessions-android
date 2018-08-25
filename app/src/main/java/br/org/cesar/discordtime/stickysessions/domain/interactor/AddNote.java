package br.org.cesar.discordtime.stickysessions.domain.interactor;

import br.org.cesar.discordtime.stickysessions.domain.model.Note;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.repository.NoteRepository;
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository;
import io.reactivex.Single;

public class AddNote extends UseCase<Note, Note> {

    private NoteRepository mNoteRepository;
    private final SessionRepository mSessionRepository;

    public AddNote(NoteRepository noteRepository, SessionRepository sessionRepository) {
        mNoteRepository = noteRepository;
        mSessionRepository = sessionRepository;
    }

    @Override
    public Single<Note> execute(final Note note) {
        return mSessionRepository.getSession(note.sessionId).flatMap(session -> {
            if (session != null && !session.id.isEmpty() &&
                session.id.equals(note.sessionId) &&
                session.topics.contains(note.topic)) {
                return mNoteRepository.addNote(note);
            } else {
                return Single.error(
                    new IllegalArgumentException(
                            "The note's session is invalid " +
                            "or it doesn't contain a topic of this session."
                    )
                );
            }
        });
    }
}
