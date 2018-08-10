package br.org.cesar.discordtime.stickysessions.ui.session;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import br.org.cesar.discordtime.stickysessions.R;
import br.org.cesar.discordtime.stickysessions.domain.interactor.EnterSession;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository;
import br.org.cesar.discordtime.stickysessions.executor.JobExecutor;
import br.org.cesar.discordtime.stickysessions.executor.MainThread;
import br.org.cesar.discordtime.stickysessions.executor.ObservableUseCase;
import br.org.cesar.discordtime.stickysessions.executor.PostExecutionThread;
import br.org.cesar.discordtime.stickysessions.presentation.session.SessionContract;
import br.org.cesar.discordtime.stickysessions.presentation.session.SessionPresenter;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class SessionActivity extends AppCompatActivity implements SessionContract.View,
        android.view.View.OnClickListener {

    private final static String TAG = "SessionActivity";
    private ViewGroup parent;
    private SessionContract.Presenter mPresenter;
    private ObservableUseCase<String, Session> mEnterSession;
    private SessionRepository sessionRepository;

    public static final String SESSION_ID = "session";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        parent = findViewById(R.id.container);

        //TODO update null repository to a SessionRepository Implementation
        mEnterSession = new ObservableUseCase<>(
                new EnterSession(null),
                new JobExecutor(),
                new MainThread());
        mPresenter = new SessionPresenter(mEnterSession);
        mPresenter.attachView(this);

        Intent intent = getIntent();
        //Enter in a session by link
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            if (uri != null) {
                String sessionId = uri.getQueryParameter(SESSION_ID);
                Log.d(TAG, "sessionId " + sessionId);
                mPresenter.onEnterSession(sessionId);
            } else {
                //TODO error message to null data
            }
            //Enter in a session by Lobby
        } else if(!TextUtils.isEmpty(intent.getStringExtra(SESSION_ID))) {
            String sessionId = intent.getStringExtra(SESSION_ID);
            Log.d(TAG, "sessionId " + sessionId);
            mPresenter.onEnterSession(sessionId);
        }
    }

    @Override
    public void onClick(android.view.View view) {

    }

    @Override
    public void displaySession() {

    }

    @Override
    public void displayError(String message) {

    }

    @Override
    public void startLoadingSession() {

    }

    @Override
    public void stopLoadingSession() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
