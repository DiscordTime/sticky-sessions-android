package br.org.cesar.discordtime.stickysessions.ui.session;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.org.cesar.discordtime.stickysessions.R;
import br.org.cesar.discordtime.stickysessions.presentation.session.TopicDetail;

public class TopicItemView extends RecyclerView.ViewHolder  {
    private static final String TAG = "TopicItemView";

    private TextView mTitleTextView;
    private Button mNewNoteButton;
    private RecyclerView mNoteListView;

    private NotesAdpater mNoteAdapter;


    public TopicItemView(@NonNull View itemView) {
        super(itemView);

        mTitleTextView = itemView.findViewById(R.id.text_title);
        mNewNoteButton = itemView.findViewById(R.id.btn_new_note);
        mNoteListView = itemView.findViewById(R.id.list_notes);

        configureNotesListView();
    }

    public static TopicItemView createView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_topic, parent, false);
        return new TopicItemView(view);
    }

    public void onBindView(TopicDetail topicDetail) {
        if (topicDetail == null) {
            Log.e(TAG, "Error during view bind because topic details is null.");
            return;
        }

        mTitleTextView.setText(topicDetail.getTopic());
        mNoteAdapter.replaceAllData(topicDetail.getNotes());

        mNewNoteButton.setOnClickListener((view) -> {
            //
        });
    }

    private void configureNotesListView() {
        mNoteAdapter = new NotesAdpater();
        mNoteListView.setAdapter(mNoteAdapter);
    }
}
