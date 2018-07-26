package br.org.cesar.discordtime.stickysessions.presentation.lobby;

public interface LobbyContract {

    interface Presenter {
        void attachView(LobbyContract.View view);
        void detachView();
        void onCreateStarfish();
        void onCreateGainPleasure();
        void onAskSessionId();
        void onEnterSession(String sessionId);
    }

    interface View {
        void displaySessionForm();
        void displayError(String message);
    }
}
