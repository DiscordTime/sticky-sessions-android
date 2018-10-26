package br.org.cesar.discordtime.stickysessions.presentation.lobby;

import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType;
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase;
import br.org.cesar.discordtime.stickysessions.logger.Logger;
import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidRouteException;
import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidViewNameException;
import br.org.cesar.discordtime.stickysessions.navigation.router.IRouter;
import br.org.cesar.discordtime.stickysessions.navigation.router.Route;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundle;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundleFactory;
import br.org.cesar.discordtime.stickysessions.ui.ExtraNames;
import io.reactivex.observers.DisposableSingleObserver;

public class LobbyPresenter implements LobbyContract.Presenter {

    private static final String TAG = "LobbyPresenter";
    private final IBundleFactory mBundleFactory;
    private LobbyContract.View mView;
    private IObservableUseCase<SessionType, Session> mCreateSession;
    private IRouter mRouter;
    private Logger mLog;
    private DisposableSingleObserver mObserver;

    public LobbyPresenter(IObservableUseCase<SessionType, Session> createSession,
                          IRouter router, Logger logger, IBundleFactory bundleFactory) {
        mCreateSession = createSession;
        mRouter = router;
        mLog = logger;
        mBundleFactory = bundleFactory;
    }

    @Override
    public void attachView(LobbyContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        mCreateSession.dispose();
    }

    @Override
    public void onClickSessionOption(LobbyContract.ActionType type) {
        mLog.d(TAG, "onClickSessionOption " + type);

        if (LobbyContract.ActionType.LIST_SESSIONS == type) {
            goNext(IRouter.LIST_SESSIONS);

        } else {
            mObserver = new CreateSessionObserver();
            SessionType sessionType = null;

            if (LobbyContract.ActionType.CREATE_GAIN_N_PLEASURE_SESSION == type) {
                sessionType = SessionType.GAIN_PLEASURE;

            } else if (LobbyContract.ActionType.CREATE_STARFISH_SESSION == type){
                sessionType = SessionType.STARFISH;
            }

            mCreateSession.execute(mObserver, sessionType);
        }
    }

    private void goNext(String event, String sessionId){
        try {
            IBundle bundle = mBundleFactory.create();
            bundle.putString(ExtraNames.SESSION_ID, sessionId);

            Route route = mRouter.getNext(mView.getName(), event);

            mView.goNext(route, bundle);
        } catch (InvalidRouteException | InvalidViewNameException e) {
            mLog.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    private void goNext(String event) {
        try {
            Route route = mRouter.getNext(mView.getName(), event);
            mView.goNext(route, null);
        } catch (InvalidRouteException | InvalidViewNameException e) {
            mLog.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onEnterSession(String sessionIdString) {
        if(sessionIdString == null || sessionIdString.isEmpty()) {
            mView.displayError("");
        } else {
            int sessionId = Integer.parseInt(sessionIdString);
            mLog.d(TAG, String.valueOf(sessionId));
        }
    }

    class CreateSessionObserver extends DisposableSingleObserver<Session> {
        @Override
        public void onSuccess(Session session) {
            mLog.d(TAG, "create session success");
            mView.stopLoading();
            goNext(IRouter.CREATED_SESSION, session.id);
        }

        @Override
        public void onError(Throwable e) {
            mLog.d(TAG, "create session error" + e.getLocalizedMessage());
            // TODO: Pass meaningful text to view depending on error
            mView.stopLoading();
            mView.displayError("Error: "+e.getLocalizedMessage());
        }


    }

    class ListSessionObserver extends DisposableSingleObserver<Void> {
        @Override
        public void onSuccess(Void aVoid) {
            mLog.d(TAG, "list sessions success");
            goNext(IRouter.LIST_SESSIONS);
        }

        @Override
        public void onError(Throwable e) {
            mLog.d(TAG, "list session error" + e.getLocalizedMessage());
            // TODO: Pass meaningful text to view depending on error
            mView.displayError("");
        }
    }

}
