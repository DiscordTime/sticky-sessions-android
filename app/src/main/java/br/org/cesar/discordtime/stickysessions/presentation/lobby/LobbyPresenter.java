package br.org.cesar.discordtime.stickysessions.presentation.lobby;

import android.util.Log;

import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType;
import br.org.cesar.discordtime.stickysessions.executor.ObservableUseCase;
import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidRouteException;
import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidViewNameException;
import br.org.cesar.discordtime.stickysessions.navigation.router.IRouter;
import br.org.cesar.discordtime.stickysessions.navigation.router.Route;
import io.reactivex.observers.DisposableSingleObserver;

public class LobbyPresenter implements LobbyContract.Presenter {

    private static final String TAG = "LobbyPresenter";
    private LobbyContract.View mView;
    private ObservableUseCase<SessionType, Session> mCreateSession;
    private IRouter mRouter;

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
    }

    @Override
    public void onCreateSession(SessionType type) {
        Log.d(TAG, "onCreateSession " + type);
        mCreateSession.execute(new CreateSessionObserver(), type);
    }

    private void goNext(String event){
        try {
            Route route = mRouter.getNext(mView.getName(), event);
            mView.goNext(route);
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
            goNext(IRouter.CREATED_SESSION);
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "create session error" + e.getLocalizedMessage());
        }
    }

}
