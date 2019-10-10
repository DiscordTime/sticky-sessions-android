package br.org.cesar.discordtime.stickysessions.presentation.notes

import br.org.cesar.discordtime.stickysessions.domain.model.Note

interface NotesContract {
    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun updateData(noteTopicDetail: INoteTopicDetail?)
        fun onNewNoteClick()
    }

    interface View {
        fun displayTopicName(name: String)
        fun displayNotes(notes: List<Note>)
        fun displayLoading()
        fun hideLoading()
        fun clearNotes()
    }
}