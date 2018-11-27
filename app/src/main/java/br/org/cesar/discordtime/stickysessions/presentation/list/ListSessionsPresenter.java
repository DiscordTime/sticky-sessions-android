package br.org.cesar.discordtime.stickysessions.presentation.list;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.R;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
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

public class ListSessionsPresenter implements ListSessionsContract.Presenter {
    private static final String TAG = "ListSessionsPresenter";
    private final IObservableUseCase<Void, List<Session>> mListSessions;
    private final IRouter mRouter;
    private final IBundleFactory mBundleFactory;
    private final Logger mLogger;
    private DisposableSingleObserver mSessionsListObserver;
    private ListSessionsContract.View mView;

    public ListSessionsPresenter(IObservableUseCase<Void, List<Session>> listSessions,
                                 IRouter router, Logger logger, IBundleFactory bundleFactory) {
        mListSessions = listSessions;
        mRouter = router;
        mLogger = logger;
        mBundleFactory = bundleFactory;
    }

    @Override
    public void attachView(ListSessionsContract.View view) {
        mLogger.d(TAG, "attached List Session view.");
        mView = view;
    }

    @Override
    public void detachView() {
        mLogger.d(TAG, "detached List Session view.");
        mView = null;
    }

    @Override
    public void onLoad() {
        mLogger.d(TAG, "onLoad list sessions ");
        initObservers();
        mView.startLoadingData();
        mListSessions.execute(mSessionsListObserver, null);
    }

    @Override
    public void onPause() {
        mLogger.d(TAG, "onPause List Session view.");
        disposeObservers();
    }

    private void disposeObservers() {
        mView = null;
        mSessionsListObserver.dispose();
    }

    private void initObservers() {
        mLogger.d(TAG, "observers started ");
        mSessionsListObserver = new DisposableSingleObserver<List<Session>>() {
            @Override
            public void onSuccess(List<Session> sessions) {
                if (mView != null) {
                    mLogger.d(TAG, "onSuccess load data ");
                    mView.stopLoadingData();
                    mView.showSessions(sessions);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (mView != null) {
                    mLogger.d(TAG, "onError load data "+e.getMessage());
                    mView.stopLoadingData();
                    mView.showError(e.getMessage());
                }
            }
        };
    }

    @Override
    public void enterOnSession(Session session) {
        if (session != null) {
            mLogger.d(TAG, "enterOnSession : "+session.id);
            IBundle bundle = mBundleFactory.create();
            goNext(session, bundle);
        } else {
            mView.stopLoadingData();
            //TODO an internacionalizable message
            mView.showError("invalid session");
        }
    }

    private void goNext(Session session, IBundle bundle) {
        bundle.putString(ExtraNames.SESSION_ID, session.id);

        try {
            Route route = mRouter.getNext(mView.getName(), IRouter.USER_SELECTED_SESSION);
            mView.goNext(route, bundle);
        } catch (InvalidRouteException e) {
            mLogger.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (InvalidViewNameException e) {
            mLogger.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }
}
