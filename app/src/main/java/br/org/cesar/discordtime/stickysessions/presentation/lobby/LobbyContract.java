package br.org.cesar.discordtime.stickysessions.presentation.lobby;

import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidViewNameException;
import br.org.cesar.discordtime.stickysessions.navigation.router.Route;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundle;

public interface LobbyContract {

    interface Presenter {
        void attachView(LobbyContract.View view);
        void detachView();
        void onClickSessionOption(ActionType type);
        void onEnterSession(String sessionId);
    }

    interface View {
        String getName();
        void goNext(Route route, IBundle bundle) throws InvalidViewNameException;
        void displayError(String message);
        void startLoading();
        void stopLoading();
    }

    enum ActionType {
        LIST_SESSIONS,
        CREATE_STARFISH_SESSION,
        CREATE_GAIN_N_PLEASURE_SESSION,
        CREATE_CUSTOM_SESSION
    }
}
