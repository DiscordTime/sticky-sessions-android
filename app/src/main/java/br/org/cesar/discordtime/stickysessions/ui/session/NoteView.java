package br.org.cesar.discordtime.stickysessions.ui.session;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.org.cesar.discordtime.stickysessions.R;
import br.org.cesar.discordtime.stickysessions.domain.model.Note;
import br.org.cesar.discordtime.stickysessions.ui.adapters.NoteAdapter;

public class NoteView extends RecyclerView.ViewHolder {
    private TextView mTitle;
    private TextView mContent;
    private ProgressBar progressBar;

    private boolean isLoading = false;
    private NoteAdapter.NoteAdapterCallback mCallback;

    public NoteView(@NonNull View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.title_note_element);
        mContent = itemView.findViewById(R.id.description_note_element);
        progressBar = itemView.findViewById(R.id.progress_bar);
    }

    public static NoteView create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_element, parent, false);
        return new NoteView(view);
    }

    public void onBindView(Note note) {
        mContent.setText(note.description);
        mTitle.setText(note.topic);
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(
                itemView.getContext(), R.animator.show_note_animator);

        animator.setTarget(itemView);
        animator.setInterpolator(new BounceInterpolator());
        animator.setDuration(750);
        animator.start();

        mContent.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int lineHeight = mContent.getLineHeight();
                        int height = mContent.getHeight();

                        if (lineHeight != 0) {
                            int maxLines = height / lineHeight;
                            mContent.setMaxLines(maxLines);
                            mContent.invalidate();
                        }

                        mContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });

        // TODO: Change it to use PublishSubject.
        itemView.setOnClickListener(v -> mCallback.onItemClicked(note));
    }

    public void setCallback(NoteAdapter.NoteAdapterCallback callback) {
        if (callback != null) {
            mCallback = callback;
        }
    }
}
