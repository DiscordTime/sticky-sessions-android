package br.org.cesar.discordtime.stickysessions.presentation.list;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase;
import br.org.cesar.discordtime.stickysessions.logger.Logger;
import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidRouteException;
import br.org.cesar.discordtime.stickysessions.navigation.router.IRouter;
import br.org.cesar.discordtime.stickysessions.navigation.router.Route;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundle;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundleFactory;
import br.org.cesar.discordtime.stickysessions.ui.ExtraNames;
import io.reactivex.observers.DisposableSingleObserver;

public class ListSessionPresenter implements ListSessionsContract.Presenter {
    private static final String TAG = "ListSessionPresenter";
    private final IObservableUseCase<Void, List<Session>> mListSessions;
    private final IRouter mRouter;
    private final IBundleFactory mBundleFactory;
    private final Logger mLogger;
    private DisposableSingleObserver mSessionsListObserver;
    private ListSessionsContract.View mView;

    public ListSessionPresenter(IObservableUseCase<Void, List<Session>> listSessions,
                                IRouter router, Logger logger, IBundleFactory bundleFactory) {
        mListSessions = listSessions;
        mRouter = router;
        mLogger = logger;
        mBundleFactory = bundleFactory;
        initObservers();
    }

    @Override
    public void attachView(ListSessionsContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        mSessionsListObserver.dispose();
    }

    @Override
    public void onLoad() {
        mListSessions.execute(mSessionsListObserver, null);
    }

    private void initObservers() {
        mSessionsListObserver = new DisposableSingleObserver<List<Session>>() {
            @Override
            public void onSuccess(List<Session> sessions) {
                if (mView != null) {
                    mView.showSessions(sessions);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (mView != null) {
                    mView.showError(e.getMessage());
                }
            }
        };
    }

    @Override
    public void enterOnSession(Session session) {
        IBundle bundle = mBundleFactory.create();
        goNext(session, bundle);
    }

    private void goNext(Session session, IBundle bundle) {
        bundle.putString(ExtraNames.SESSION_ID, session.id);

        try {
            Route route = mRouter.getNext(mView.getName(), IRouter.LISTED_SESSION);
            mView.goNext(route, bundle);

        } catch (InvalidRouteException e) {
            mLogger.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }
}
