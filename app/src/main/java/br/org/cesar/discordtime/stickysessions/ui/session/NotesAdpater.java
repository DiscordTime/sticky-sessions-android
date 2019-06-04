package br.org.cesar.discordtime.stickysessions.ui.session;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.domain.model.Note;

public class NotesAdpater extends RecyclerView.Adapter<NoteView> {

    private List<Note> mNotes;

    @NonNull
    @Override
    public NoteView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return NoteView.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteView holder, int position) {
        holder.onBindView(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mNotes != null ? mNotes.size() : 0;
    }

    public void replaceAllData(List<Note> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }

    private Note getItem(int position) {
        return mNotes != null ? mNotes.get(position) : null;
    }
}
