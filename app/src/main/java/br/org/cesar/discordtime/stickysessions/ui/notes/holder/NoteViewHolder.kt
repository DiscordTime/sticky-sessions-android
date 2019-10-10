package br.org.cesar.discordtime.stickysessions.ui.notes.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.org.cesar.discordtime.stickysessions.R
import br.org.cesar.discordtime.stickysessions.domain.model.Note

class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mTitle = itemView.findViewById<TextView>(R.id.text_note_topic)
    private val mContent = itemView.findViewById<TextView>(R.id.text_note_description)
    private val menuView = itemView.findViewById<ImageView>(R.id.image_menu)

    fun updateData(note: Note) {
        mContent?.text = note.description
        mTitle?.text = note.topic

        menuView.setOnClickListener {
            // TODO: Show option menu
        }

        itemView.setOnClickListener {
            // TODO: Implement that
        }
    }
}