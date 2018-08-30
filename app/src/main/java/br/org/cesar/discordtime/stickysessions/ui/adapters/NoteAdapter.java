package br.org.cesar.discordtime.stickysessions.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import br.org.cesar.discordtime.stickysessions.R;
import br.org.cesar.discordtime.stickysessions.domain.model.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> mNotes;
    private NoteAdapterCallback mCallback;
    private Animation mAnimationShow;

    public NoteAdapter(Context context) {
        mNotes = new ArrayList<>();
        mAnimationShow = AnimationUtils.loadAnimation(context, R.anim.show_animation_note);
    }

    public void addNote(Note note) {
        mNotes.add(note);
        notifyItemChanged(mNotes.size() - 1);
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
        holder.itemView.startAnimation(mAnimationShow);
    }

    @Override
    public int getItemCount() {
        return mNotes == null ? 0 : mNotes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;
        TextView mContent;

        NoteViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mTitle = itemView.findViewById(R.id.title_note_element);
            mContent = itemView.findViewById(R.id.description_note_element);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Note note = mNotes.get(position);
            mCallback.onItemClicked(note);
        }
    }
}
