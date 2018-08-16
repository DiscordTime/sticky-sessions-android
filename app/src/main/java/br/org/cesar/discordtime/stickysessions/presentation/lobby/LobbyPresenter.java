package br.org.cesar.discordtime.stickysessions.presentation.lobby;

import android.util.Log;

import br.org.cesar.discordtime.stickysessions.Injector;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType;
import br.org.cesar.discordtime.stickysessions.executor.ObservableUseCase;
import io.reactivex.observers.DisposableSingleObserver;

public class LobbyPresenter implements LobbyContract.Presenter {

    private static final String TAG = "LobbyPresenter";
    private LobbyContract.View mView;
    private ObservableUseCase<SessionType, Session> mCreateSession;

    public LobbyPresenter() {
        mCreateSession = Injector.provideCreateSessionUseCase();
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
    public void onCreateStarfish() {
        Log.d(TAG, "onCreateStarfish");
        mCreateSession.execute(new CreateSessionObserver(), SessionType.STARFISH);

    }

    @Override
    public void onCreateGainPleasure() {
        Log.d(TAG, "onCreateGainPleasure");
        mCreateSession.execute(new CreateSessionObserver(), SessionType.GAIN_PLEASURE);
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
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "create session error" + e.getLocalizedMessage());
        }
    }

}
