package br.org.cesar.discordtime.stickysessions.presentation.notes

import android.util.Log
import br.org.cesar.discordtime.stickysessions.domain.model.Note
import br.org.cesar.discordtime.stickysessions.domain.model.NoteFilter
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase
import io.reactivex.observers.DisposableSingleObserver

class NotesPresenter(
        private val mListNotes: IObservableUseCase<NoteFilter, List<Note>>?,
        private val addNote: IObservableUseCase<Note, Note>?,
        private val removeNote: IObservableUseCase<Note, Boolean>?
) : NotesContract.Presenter {
    companion object {
        const val TAG = "NotesPresenter"
    }

    private var mView: NotesContract.View? = null
    private var mNoteTopicDetail: INoteTopicDetail? = null

    override fun attachView(view: NotesContract.View) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }

    override fun updateData(noteTopicDetail: INoteTopicDetail?) {
        mNoteTopicDetail = noteTopicDetail
        mView?.clearNotes()
        mView?.displayTopicName(noteTopicDetail?.getTopicName() ?: "")

        loadNotesForSession()
    }

    override fun onNewNoteClick() {
        // TODO: Open Note Editor as new note.
    }

    private fun getTopicName(): String = mNoteTopicDetail?.getTopicName() ?: ""

    private fun loadNotesForSession() {
        mView?.displayLoading()
        if (getTopicName() == "Start") {
            mView?.displayNotes(getStart())
        } else {
            mView?.displayNotes(listOf(Note(
                    "Description Lorem ipsum dolor sit amet, cons ect etur adipiscai elit, sed do eiusmod tempor. Lorem ipsum dolor sit amet, cons ect etur adipiscai elit, sed do eiusmod tempor.",
                    "user 1",
                    mNoteTopicDetail?.getTopicName(),
                    "session id")))
        }
        mView?.hideLoading()

//        mListNotes?.execute(object: DisposableSingleObserver<List<Note>>() {
//            override fun onSuccess(notes: List<Note>) {
//                // TODO: Move this logic to a use case.
//                mView?.displayNotes(
//                        notes.filter { note -> note.topic == mNoteTopicDetail?.getTopicName() })
//                mView?.hideLoading()
//            }
//
//            override fun onError(e: Throwable) {
//                Log.e(TAG, "displayNotes: " + e.message)
//                mView?.hideLoading()
//                // TODO: show error on UI.
//            }
//        }, NoteFilter(mNoteTopicDetail?.getSessionId()))
    }

    private fun getStart(): List<Note> = listOf(Note(
            "Description Lorem ipsum dolor sit amet, cons ect etur adipiscai elit, sed do eiusmod tempor. Lorem ipsum dolor sit amet, cons ect etur adipiscai elit, sed do eiusmod tempor.",
            "user 1",
            mNoteTopicDetail?.getTopicName(),
            "session id"),
            Note(
                    "Testando, primeira nota",
                    "user 1",
                    mNoteTopicDetail?.getTopicName(),
                    "session id"))
}
