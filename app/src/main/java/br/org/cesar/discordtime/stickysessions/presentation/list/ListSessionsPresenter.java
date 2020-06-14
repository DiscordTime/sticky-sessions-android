package br.org.cesar.discordtime.stickysessions.presentation.list;

import java.io.IOException;
import java.util.List;

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
    private final IObservableUseCase<String, List<Session>> mListSessions;
    private final IObservableUseCase<Session, Session> mRescheduleSession;
    private final IRouter mRouter;
    private final IBundleFactory mBundleFactory;
    private final Logger mLogger;
    private DisposableSingleObserver mListSessionsObserver;
    private DisposableSingleObserver mRescheduleSessionObserver;
    private ListSessionsContract.View mView;
    private Session mSessionToBeRescheduled;

    public ListSessionsPresenter(IObservableUseCase<String, List<Session>> listSessions,
                                 IObservableUseCase<Session, Session> rescheduleSession,
                                 IRouter router, Logger logger, IBundleFactory bundleFactory) {
        mListSessions = listSessions;
        mRescheduleSession = rescheduleSession;
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
    public void onLoad(String meetingId) {
        mLogger.d(TAG, "onLoad list sessions ");

        if (meetingId != null && !meetingId.isEmpty()) {
            initObservers();
            mView.startLoadingData();
            mListSessions.execute(mListSessionsObserver, meetingId);
        } else {
            // TODO: Create a ResourceWrapper and get string from there
            mView.showError("Invalid meeting, please go back to the meetings screen");
        }

    }

    @Override
    public void onStop() {
        mLogger.d(TAG, "onPause List Session view.");
        disposeObservers();
    }

    private void disposeObservers() {
        mView = null;
        if (mListSessionsObserver != null) mListSessionsObserver.dispose();
    }

    private void initObservers() {
        mLogger.d(TAG, "observers started");
        mListSessionsObserver = new DisposableSingleObserver<List<Session>>() {
            @Override
            public void onSuccess(List<Session> sessions) {
                if (mView != null) {
                    mLogger.d(TAG, "onSuccess load data");
                    mView.stopLoadingData();
                    mView.showSessions(sessions);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (mView != null) {

                    String error = e.getMessage();
                    mLogger.e(TAG, "onError load data ", e);
                    mView.stopLoadingData();

                    if (e instanceof IOException) {
                        error = "Failed to connect to server. Please try again";
                        mView.showRetryOption();
                    }
                    mView.showError(error);
                }
            }
        };
        mRescheduleSessionObserver = new DisposableSingleObserver<Session>() {
            @Override
            public void onSuccess(Session session) {
                if (mView != null) {
                    mLogger.d(TAG, "onSuccess rescheduling session");
                    mView.stopLoadingData();
                    mView.refreshSession(session);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (mView != null) {
                    String error = e.getMessage();
                    mLogger.d(TAG, "onError rescheduling session " + error);
                    mView.stopLoadingData();
                    mView.showError(error);
                    mView.refreshList();
                }
            }
        };
    }

    @Override
    public void enterOnSession(Session session) {
        if (session != null) {
            mLogger.d(TAG, "enterOnSession : " + session.id);
            IBundle bundle = mBundleFactory.create();
            goNext(session, bundle);
        } else {
            mView.stopLoadingData();
            //TODO an internacionalizable message
            mView.showError("invalid session");
        }
    }

    @Override
    public void onSwipeLeft(Session session) {
        mSessionToBeRescheduled = session;
        mView.showDatePicker(session.getYear(), session.getMonth(), session.getDay());
    }

    @Override
    public void onDateSelected(int year, int month, int day) {
        if (mSessionToBeRescheduled != null) {
            Session session = new Session();
            session.copy(mSessionToBeRescheduled);
            session.setCreatedAt(year, month, day);
            mView.startLoadingData();
            mRescheduleSession.execute(mRescheduleSessionObserver, session);
        }

        mSessionToBeRescheduled = null;
    }

    private void goNext(Session session, IBundle bundle) {
        if (mView != null) {
            bundle.putString(ExtraNames.SESSION_ID, session.id);

            try {
                Route route = mRouter.getNext(mView.getName(), IRouter.USER_SELECTED_SESSION);
                mView.goNext(route, bundle);
            } catch (InvalidRouteException | InvalidViewNameException e) {
                mLogger.e(TAG, e.getMessage(), e);
            }
        }
    }
}
