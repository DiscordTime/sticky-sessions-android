package br.org.cesar.discordtime.stickysessions.presentation.session;

import java.util.List;
import br.org.cesar.discordtime.stickysessions.domain.model.Note;
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType;

public interface SessionContract {

    interface Presenter {
        void attachView(SessionContract.View view);
        void detachView();
        void onShareSession();
        void onAddNoteClicked();
        void addNewNote(String sessionId, String description);
        void onNoteWidgetClicked(Note note);
        void onResume();
        void currentUser(String userName);
        void currentSession(String sessionId);
        void removeNote(Note note);
        SessionType getSessionType();
        String getSessionDate();
    }

    interface View {
        void addNoteToNoteList(Note note);
        void shareSession(String sessionId);
        void cleanNotes();
        void removeNote(Note note);
        void startLoadingNote();
        void stopLoadingNote();
        void startLoadingAllNotes();
        void stopLoadingAllNotes();
        void showWidgetAddName();
        void displayAddNoteDialog(List<String> topics);
        void displaySession();
        void displayError(String message);
        void displayNotes(List<Note> notes);
        void displayErrorInvalidNotes();
        void displayNoteContent(Note note);
    }
}
