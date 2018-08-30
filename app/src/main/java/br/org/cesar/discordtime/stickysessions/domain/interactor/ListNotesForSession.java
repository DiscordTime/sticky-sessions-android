package br.org.cesar.discordtime.stickysessions.domain.interactor;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.domain.model.Note;
import br.org.cesar.discordtime.stickysessions.domain.repository.NoteRepository;
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository;
import io.reactivex.Single;

public class ListNotesForSession extends UseCase<String, List<Note>> {

    private final NoteRepository mNoteRepository;
    private final SessionRepository mSessionRepository;

    public ListNotesForSession(NoteRepository noteRepository, SessionRepository sessionRepository) {
        mNoteRepository = noteRepository;
        mSessionRepository = sessionRepository;
    }

    @Override
    public Single<List<Note>> execute(String sessionId) {
        return mSessionRepository.getSession(sessionId).flatMap(
            session -> {
                if (session == null) {
                    return Single.error(new Exception("The Session does not exist"));
                }

                return mNoteRepository.listNotesForSession(sessionId);
            }
        );
    }
}
