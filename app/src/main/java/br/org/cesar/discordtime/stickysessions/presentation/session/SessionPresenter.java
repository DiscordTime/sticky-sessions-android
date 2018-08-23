package br.org.cesar.discordtime.stickysessions.presentation.session;

import android.util.Log;

import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.executor.ObservableUseCase;
import io.reactivex.observers.DisposableSingleObserver;

public class SessionPresenter implements SessionContract.Presenter {

    private static final String TAG = "SessionPresenter";
    private ObservableUseCase<String, Session> mEnterSession;
    private SessionContract.View mView;
    private Session mActiveSession;

    public SessionPresenter(ObservableUseCase enterSession) {
        mEnterSession = enterSession;
    }

    @Override
    public void attachView(SessionContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onLoadSession() {
        Log.d(TAG, "onLoadSession");
        mView.startLoadingSession();
    }

    @Override
    public void onStopLoadSession() {
        Log.d(TAG, "onStopLoadSession");
        mView.stopLoadingSession();
    }

    @Override
    public void onEnterSession(String sessionId) {
        Log.d(TAG, "onEnterSession : "+sessionId);
        onLoadSession();
        mEnterSession.execute(new DisposableSingleObserver() {
            @Override
            public void onSuccess(Object o) {
                mActiveSession = (Session) o;
                mView.displaySession();
                onStopLoadSession();
            }

            @Override
            public void onError(Throwable e) {
                onStopLoadSession();
                mView.displayError(e.getMessage());
            }
        }, sessionId);
    }

    @Override
    public void onShareSession() {
        if(mActiveSession != null) {
            mView.shareSession(mActiveSession.id);
        }
    }
}
