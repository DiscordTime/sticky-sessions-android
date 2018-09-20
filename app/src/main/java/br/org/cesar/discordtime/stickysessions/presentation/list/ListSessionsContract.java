package br.org.cesar.discordtime.stickysessions.presentation.list;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.navigation.router.Route;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundle;

public interface ListSessionsContract {

    interface Presenter {
        void attachView(ListSessionsContract.View view);
        void detachView();
        void onLoad();
        void enterOnSession(Session session);
    }

    interface View {
        void showSessions(List<Session> sessions);
        void onClickSessionWidgetItem(Session session);
        void showError(String message);
        String getName();
        void goNext(Route route, IBundle bundle);
    }

}
