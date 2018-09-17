package br.org.cesar.discordtime.stickysessions.ui.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import br.org.cesar.discordtime.stickysessions.R;
import br.org.cesar.discordtime.stickysessions.domain.model.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private final Context mContext;
    private List<Note> mNotes;
    private NoteAdapterCallback mCallback;

    public NoteAdapter(Context context) {
        mContext = context;
        mNotes = new ArrayList<>();
    }

    public void addNote(Note note) {
        mNotes.add(note);
        notifyItemInserted(mNotes.size() - 1);
    }

    public void removeNote(Note note) {
        for (int i = 0; i < mNotes.size(); i++) {
            Note element = mNotes.get(i);
            if (note.equals(element)) {
                mNotes.remove(note);
                notifyItemRemoved(i);
            }
        }
    }

    public interface NoteAdapterCallback {
        void onItemClicked(Note note);
    }

    public void setNotes(List<Note> notes) {

        if (notes == null) {
            throw new InvalidParameterException("note list could not be null");
        }

        mNotes.clear();
        mNotes.addAll(notes);
        // TODO: fix animation crash -> IndexOutOfBoundsException: Inconsistency detected.
        //notifyItemRangeInserted(0, mNotes.size());
        notifyDataSetChanged();
    }

    public void setCallback(NoteAdapterCallback callback) {
        if (callback != null) {
            mCallback = callback;
        }
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.note_element, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = mNotes.get(position);
        holder.mContent.setText(note.description);
        holder.mTitle.setText(note.topic);

        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(
            mContext, R.animator.show_note_animator);

        animator.setTarget(holder.itemView);
        animator.setInterpolator(new BounceInterpolator());
        animator.setDuration(750);
        animator.start();

        holder.mContent.getViewTreeObserver().addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int lineHeight = holder.mContent.getLineHeight();
                int height = holder.mContent.getHeight();

                if (lineHeight != 0) {
                    int maxLines = height / lineHeight;
                    holder.mContent.setMaxLines(maxLines);
                    holder.mContent.invalidate();
                }

                holder.mContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes == null ? 0 : mNotes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mContent;

        NoteViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Note note = mNotes.get(position);
                    mCallback.onItemClicked(note);
                }
            });

            mTitle = itemView.findViewById(R.id.title_note_element);
            mContent = itemView.findViewById(R.id.description_note_element);
        }

    }
}
