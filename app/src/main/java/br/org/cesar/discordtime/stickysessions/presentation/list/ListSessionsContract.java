package br.org.cesar.discordtime.stickysessions.presentation.list;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidViewNameException;
import br.org.cesar.discordtime.stickysessions.navigation.router.Route;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundle;

public interface ListSessionsContract {

    interface Presenter {
        void attachView(ListSessionsContract.View view);
        void detachView();
        void onLoad();
        void onPause();
        void enterOnSession(Session session);
    }

    interface View {
        void startLoadingData();
        void stopLoadingData();
        void showSessions(List<Session> sessions);
        void showError(String message);
        void showRetryOption();
        String getName();
        void goNext(Route route, IBundle bundle) throws InvalidViewNameException;
    }

}
