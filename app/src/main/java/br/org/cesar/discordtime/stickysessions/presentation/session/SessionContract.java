package br.org.cesar.discordtime.stickysessions.presentation.session;

import java.util.List;

public interface SessionContract {

    interface Presenter {
        void attachView(SessionContract.View view);
        void detachView();
        void onLoadSession();
        void onStopLoadSession();
        void onEnterSession(String sessionId);
        void onShareSession();
        void onAddNoteClicked();
        void addNewNote(String sessionId, String description);
    }

    interface View {
        void displayAddNoteDialog(List<String> topics);
        void displaySession();
        void displayError(String message);
        void startLoadingSession();
        void stopLoadingSession();
        void shareSession(String sessionId);
        void showAddNoteSuccessfullyMessage();
    }
}
