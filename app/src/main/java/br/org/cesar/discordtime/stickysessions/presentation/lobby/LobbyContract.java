package br.org.cesar.discordtime.stickysessions.presentation.lobby;

import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidViewNameException;
import br.org.cesar.discordtime.stickysessions.navigation.router.Route;

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
        String getName();
        void goNext(Route route) throws InvalidViewNameException;
        void displaySessionForm();
        void displayError(String message);
    }
}
