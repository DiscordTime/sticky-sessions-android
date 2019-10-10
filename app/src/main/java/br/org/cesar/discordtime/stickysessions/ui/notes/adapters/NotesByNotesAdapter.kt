package br.org.cesar.discordtime.stickysessions.ui.notes.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.org.cesar.discordtime.stickysessions.presentation.notes.INoteTopicDetail
import br.org.cesar.discordtime.stickysessions.ui.notes.holder.NotesViewHolder

class NotesByNotesAdapter : RecyclerView.Adapter<NotesViewHolder>() {
    private var mNoteTopicDetailList: List<INoteTopicDetail> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = NotesViewHolder.createView(parent)

    override fun getItemCount(): Int = mNoteTopicDetailList.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) =
        holder.getPresenter().updateData(getItem(position))


    fun replaceData(sessionDetail: List<INoteTopicDetail>) {
        mNoteTopicDetailList = sessionDetail
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): INoteTopicDetail? = mNoteTopicDetailList[position]
}