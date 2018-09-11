package br.org.cesar.discordtime.stickysessions.presentation.session;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.domain.model.Note;

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
    }

    interface View {
        void displayAddNoteDialog(List<String> topics);
        void displaySession();
        void displayError(String message);
        void startLoadingSession();
        void stopLoadingSession();
        void shareSession(String sessionId);
        void displayNotes(List<Note> notes);
        void displayErrorInvalidNotes();
        void displayNoteContent(Note note);
        void addNoteToNoteList(Note note);
        void showWidgetAddName();
        void cleanNotes();
        void removeNote(Note note);
    }
}
