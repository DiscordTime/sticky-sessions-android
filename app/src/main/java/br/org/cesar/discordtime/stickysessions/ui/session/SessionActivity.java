package br.org.cesar.discordtime.stickysessions.ui.session;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.org.cesar.discordtime.stickysessions.R;
import br.org.cesar.discordtime.stickysessions.app.StickySessionApplication;
import br.org.cesar.discordtime.stickysessions.domain.model.Note;
import br.org.cesar.discordtime.stickysessions.presentation.session.SessionContract;
import br.org.cesar.discordtime.stickysessions.ui.adapters.NoteAdapter;

public class SessionActivity extends AppCompatActivity implements SessionContract.View,
        View.OnClickListener, NoteAdapter.NoteAdapterCallback {

    private final static String TAG = "SessionActivity";
    private Context mContext;
    private ViewGroup parent;
    private View mLoadingView;
    private RecyclerView mRecyclerView;

    @Inject
    SessionContract.Presenter mPresenter;

    public static final String SESSION_ID = "session";
    private NoteAdapter mNoteAdapter;
    private View mAddNewNoteView;
    private Animation mAnimationShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        ((StickySessionApplication)getApplicationContext()).inject(this);
        mContext = this;

        bindView();
        enterSession();
    }

    private void bindView() {
        parent = findViewById(R.id.container);
        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        mPresenter.attachView(this);

        mAddNewNoteView = findViewById(R.id.add_note_view);
        mAddNewNoteView.setOnClickListener(this);

        mLoadingView = findViewById(R.id.loading_preview);

        mRecyclerView = findViewById(R.id.user_notes_recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mNoteAdapter = new NoteAdapter(this);
        mNoteAdapter.setCallback(this);

        mRecyclerView.setAdapter(mNoteAdapter);
        mAnimationShow = AnimationUtils.loadAnimation(this, R.anim.show_animation);
    }

    private void enterSession() {
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
    protected void onResume() {
        super.onResume();
        mAddNewNoteView.startAnimation(mAnimationShow);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.session_menu, menu);
        return true;
    }

    @Override
    public void onClick(android.view.View view) {
        switch (view.getId()) {
            case R.id.add_note_view:
                mPresenter.onAddNoteClicked();
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == item.getItemId()) {
            mPresenter.onShareSession();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void displayNoteContent(Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final android.view.View view =
            inflater.inflate(R.layout.note_element_dialog, parent, false);

        TextView title = view.findViewById(R.id.title_note_element);
        title.setText(note.topic);

        TextView content = view.findViewById(R.id.description_note_element);
        content.setText(note.description);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.AnimationDialogNote;
        alertDialog.show();
    }

    @Override
    public void addNoteToNoteList(Note note) {
        mNoteAdapter.addNote(note);
    }

    @Override
    public void displaySession() {

    }

    @Override
    public void displayError(String message) {

    }

    @Override
    public void startLoadingSession() {
        mLoadingView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void stopLoadingSession() {
        mLoadingView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
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
    public void displayNotes(List<Note> notes) {
        mNoteAdapter.setNotes(notes);
    }

    @Override
    public void displayErrorInvalidNotes() {
        Toast.makeText(mContext, "Invalid Notes for Session", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onItemClicked(Note note) {
        mPresenter.onNoteWidgetClicked(note);
    }
}
