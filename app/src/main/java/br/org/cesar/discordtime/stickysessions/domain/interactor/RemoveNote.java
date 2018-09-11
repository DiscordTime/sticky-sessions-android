package br.org.cesar.discordtime.stickysessions.domain.interactor;

import br.org.cesar.discordtime.stickysessions.domain.model.Note;
import br.org.cesar.discordtime.stickysessions.domain.repository.NoteRepository;
import io.reactivex.Single;

public class RemoveNote extends UseCase<Note, Boolean> {
    private final NoteRepository mNoteRepository;

    public RemoveNote(NoteRepository noteRepository) {
        mNoteRepository = noteRepository;
    }

    @Override
    public Single<Boolean> execute(Note note) {
        return mNoteRepository.removeNote(note);
    }
}
