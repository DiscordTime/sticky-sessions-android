package br.org.cesar.discordtime.stickysessions.presentation.lobby;

import android.os.Bundle;
import android.util.Log;

import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType;
import br.org.cesar.discordtime.stickysessions.executor.ObservableUseCase;
import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidRouteException;
import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidViewNameException;
import br.org.cesar.discordtime.stickysessions.navigation.router.IRouter;
import br.org.cesar.discordtime.stickysessions.navigation.router.Route;
import br.org.cesar.discordtime.stickysessions.ui.ExtraNames;
import io.reactivex.observers.DisposableSingleObserver;

public class LobbyPresenter implements LobbyContract.Presenter {

    private static final String TAG = "LobbyPresenter";
    private LobbyContract.View mView;
    private ObservableUseCase<SessionType, Session> mCreateSession;
    private IRouter mRouter;
    private CreateSessionObserver mObserver;

    public LobbyPresenter(ObservableUseCase<SessionType, Session> createSession, IRouter router) {
        mCreateSession = createSession;
        mRouter = router;
    }

    @Override
    public void attachView(LobbyContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        if (mObserver != null && !mObserver.isDisposed()) {
            mObserver.dispose();
        }
    }

    @Override
    public void onCreateSession(SessionType type) {
        Log.d(TAG, "onCreateSession " + type);
        mObserver = new CreateSessionObserver();
        mCreateSession.execute(mObserver, type);
    }

    private void goNext(String event, String sessionId){
        try {
            Bundle bundle = new Bundle();
            bundle.putString(ExtraNames.SESSION_ID, sessionId);

            Route route = mRouter.getNext(mView.getName(), event);

            mView.goNext(route, bundle);
        } catch (InvalidRouteException | InvalidViewNameException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onAskSessionId() {
        mView.displaySessionForm();
    }

    @Override
    public void onEnterSession(String sessionIdString) {
        if( sessionIdString == null) {
            mView.displayError("");
        } else {
            int sessionId = Integer.parseInt(sessionIdString);
            Log.d(TAG, String.valueOf(sessionId));
        }
    }

    class CreateSessionObserver extends DisposableSingleObserver<Session> {
        @Override
        public void onSuccess(Session session) {
            Log.d(TAG, "create session success");
            goNext(IRouter.CREATED_SESSION, session.id);
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "create session error" + e.getLocalizedMessage());
        }
    }

}
