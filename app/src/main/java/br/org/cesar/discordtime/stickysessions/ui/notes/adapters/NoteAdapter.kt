package br.org.cesar.discordtime.stickysessions.ui.notes.adapters;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.org.cesar.discordtime.stickysessions.R
import br.org.cesar.discordtime.stickysessions.domain.model.Note
import br.org.cesar.discordtime.stickysessions.ui.notes.holder.NoteViewHolder

class NoteAdapter : RecyclerView.Adapter<NoteViewHolder>() {
    private var notes: MutableList<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
                R.layout.item_note, parent, false);
        return NoteViewHolder(view);
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.updateData(getItem(position))
    }

    fun addNote(note: Note) {
        notes.add(note)
        notifyItemInserted(notes.size - 1)
    }

    fun removeNote(note: Note) {
        notes.indexOf(note).let {
            if (it >= 0) {
                notes.remove(note)
                notifyItemRemoved(it)
            }
        }
    }

    fun setNotes(notes: List<Note>) {
        this.notes.clear()
        this.notes.addAll(notes)
        // TODO: fix animation crash -> IndexOutOfBoundsException: Inconsistency detected.
        //notifyItemRangeInserted(0, mNotes.size())
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): Note = notes[position]
}
