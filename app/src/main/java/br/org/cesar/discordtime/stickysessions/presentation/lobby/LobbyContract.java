package br.org.cesar.discordtime.stickysessions.presentation.lobby;

import android.os.Bundle;

import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidViewNameException;
import br.org.cesar.discordtime.stickysessions.navigation.router.Route;
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType;

public interface LobbyContract {

    interface Presenter {
        void attachView(LobbyContract.View view);
        void detachView();
        void onCreateSession(SessionType type);
        void onAskSessionId();
        void onEnterSession(String sessionId);
    }

    interface View {
        String getName();
        void goNext(Route route, Bundle bundle) throws InvalidViewNameException;
        void displaySessionForm();
        void displayError(String message);
    }
}
