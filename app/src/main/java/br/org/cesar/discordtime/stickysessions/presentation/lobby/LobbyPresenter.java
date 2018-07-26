package br.org.cesar.discordtime.stickysessions.presentation.lobby;

import android.util.Log;

public class LobbyPresenter implements LobbyContract.Presenter {

    private static final String TAG = "LobbyPresenter";
    private LobbyContract.View mView;

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
    }

    @Override
    public void onCreateGainPleasure() {
        Log.d(TAG, "onCreateGainPleasure");
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
}
