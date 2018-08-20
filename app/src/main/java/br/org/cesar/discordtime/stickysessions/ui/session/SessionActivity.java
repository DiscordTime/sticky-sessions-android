package br.org.cesar.discordtime.stickysessions.ui.session;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import br.org.cesar.discordtime.stickysessions.R;
import br.org.cesar.discordtime.stickysessions.app.StickySessionApplication;
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository;
import br.org.cesar.discordtime.stickysessions.presentation.session.SessionContract;

public class SessionActivity extends AppCompatActivity implements SessionContract.View,
        android.view.View.OnClickListener {

    private final static String TAG = "SessionActivity";
    private Context mContext;
    private ViewGroup parent;

    @Inject
    SessionContract.Presenter mPresenter;
    private SessionRepository sessionRepository;

    public static final String SESSION_ID = "session";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        ((StickySessionApplication)getApplicationContext()).inject(this);

        mContext = this;

        parent = findViewById(R.id.container);
        Button btShareSession = findViewById(R.id.bt_share);
        btShareSession.setOnClickListener(this);
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

        View addNewNoteView = findViewById(R.id.add_note_view);
        addNewNoteView.setOnClickListener(this);
    }



    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {
            case R.id.bt_share:
                mPresenter.onShareSession();
                break;
            case R.id.add_note_view:
                mPresenter.onAddNoteClicked();
                break;

            default:
                break;
        }
    }

    @Override
    public void displayAddNoteDialog(final List<String> topics) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final android.view.View view =
            inflater.inflate(R.layout.dialog_add_new_note, parent, false);

        final Spinner spinner = view.findViewById(R.id.session_spinner);
        spinner.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1,
            topics));

        final EditText editText = view.findViewById(R.id.note_description);

        builder.setView(view);
        builder.setMessage(getString(R.string.note_dialog_add_title));
        builder.setPositiveButton(getString(R.string.confirm),
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String topic = topics.get(spinner.getSelectedItemPosition());
                    String description = editText.getText().toString();
                    mPresenter.addNewNote(topic, description);
                }
            }
        );

        builder.setNegativeButton(getString(R.string.cancel),
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }
        );
        builder.show();
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
    public void shareSession(String sessionId){
        Intent sendIntent=new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT,
        String.format(getString(R.string.share_session),sessionId));
        startActivity(sendIntent);
    }

    public void showAddNoteSuccessfullyMessage() {
        Toast.makeText(mContext, R.string.new_note_added_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
