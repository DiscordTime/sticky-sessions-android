package br.org.cesar.discordtime.stickysessions.ui.session;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

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
import br.org.cesar.discordtime.stickysessions.ui.ExtraNames;
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
        configureSession();
    }

    private void bindView() {
        parent = findViewById(R.id.container);
        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        mPresenter.attachView(this);

        mAddNewNoteView = findViewById(R.id.add_note_view);
        mAddNewNoteView.setOnClickListener(this);
        mAddNewNoteView.setVisibility(View.INVISIBLE);

        mLoadingView = findViewById(R.id.loading_preview);

        mRecyclerView = findViewById(R.id.user_notes_recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mNoteAdapter = new NoteAdapter(this);
        mNoteAdapter.setCallback(this);

        mRecyclerView.setAdapter(mNoteAdapter);
        mAnimationShow = AnimationUtils.loadAnimation(this, R.anim.show_animation);
    }

    private void configureSession() {
        Intent intent = getIntent();
        //Enter in a session by link
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            if (uri != null) {
                String sessionId = uri.getQueryParameter(ExtraNames.SESSION_ID);
                Log.d(TAG, "sessionId " + sessionId);
                mPresenter.currentSession(sessionId);
            } else {
                //TODO error message to null data
            }
            //Enter in a session by Lobby
        } else if(!TextUtils.isEmpty(intent.getStringExtra(ExtraNames.SESSION_ID))) {
            String sessionId = intent.getStringExtra(ExtraNames.SESSION_ID);
            Log.d(TAG, "sessionId " + sessionId);
            mPresenter.currentSession(sessionId);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        cleanNotes();
        configureSession();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
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
        if(item.getItemId() == R.id.action_share) {
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

        final ChipGroup chipGroup = view.findViewById(R.id.session_elements);

        for (int i = 0; i < topics.size(); i++) {
            String topic = topics.get(i);
            Chip chip = (Chip) inflater.inflate(R.layout.chip_element, parent, false);
            chip.setText(topic);
            chip.setId(i);
            chipGroup.addView(chip);
        }

        final EditText editText = view.findViewById(R.id.note_description);
        View confirm = view.findViewById(R.id.confirm);
        View cancel = view.findViewById(R.id.cancel);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = chipGroup.getCheckedChipId();
                if (position > 0) {
                    String topic = topics.get(position);

                    String description = editText.getText().toString();
                    mPresenter.addNewNote(topic, description);
                    alertDialog.cancel();
                } else {
                    showShouldChoiceTopicMessage();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        alertDialog.show();
    }

    private void showShouldChoiceTopicMessage() {
        Toast.makeText(this, R.string.select_topic_dialog_message, Toast.LENGTH_SHORT).show();
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
    public void showWidgetAddName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AddUserContent);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final android.view.View view =
            inflater.inflate(R.layout.user_input_dialog, parent, false);

        final Button btConfirm = view.findViewById(R.id.bt_positive);
        EditText editText = view.findViewById(R.id.user_name_dialog);
        editText.requestFocus();

        builder.setTitle(R.string.enter_your_name);
        builder.setCancelable(false);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = editText.getText().toString();
                currentUser(userName);
                alertDialog.dismiss();
            }
        });

        final Button btCancel = view.findViewById(R.id.bt_negative);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        alertDialog.show();
    }

    @Override
    public void cleanNotes() {
        mNoteAdapter = new NoteAdapter(this);
        mRecyclerView.setAdapter(mNoteAdapter);
    }

    private void currentUser(String userName) {
        mPresenter.currentUser(userName);
    }

    @Override
    public void displaySession() {
        mAddNewNoteView.startAnimation(mAnimationShow);
        mAddNewNoteView.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayError(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
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
