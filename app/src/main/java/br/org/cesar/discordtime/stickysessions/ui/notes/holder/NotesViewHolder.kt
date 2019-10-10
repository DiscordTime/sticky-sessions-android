package br.org.cesar.discordtime.stickysessions.ui.notes.holder

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.org.cesar.discordtime.stickysessions.R
import br.org.cesar.discordtime.stickysessions.app.StickySessionApplication
import br.org.cesar.discordtime.stickysessions.domain.model.Note
import br.org.cesar.discordtime.stickysessions.presentation.notes.NotesContract
import br.org.cesar.discordtime.stickysessions.ui.notes.adapters.NoteAdapter
import java.util.ArrayList
import javax.inject.Inject

class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        NotesContract.View, View.OnClickListener {

    private val mTopicNameTextView = itemView.findViewById<TextView>(R.id.text_note_topic)
    private val mNotesList = itemView.findViewById<RecyclerView>(R.id.list_notes)
    private val mNewNoteButton = itemView.findViewById<Button>(R.id.btn_new_note)
    private val mLoadingView = itemView.findViewById<View>(R.id.loading)

    private lateinit var mNoteAdapter: NoteAdapter

    @Inject
    lateinit var mPresenter: NotesContract.Presenter

    companion object {
        fun createView(parent: ViewGroup): NotesViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_topic_notes, parent, false)
            return NotesViewHolder(view)
        }
    }

    init {
        (itemView.context.applicationContext as StickySessionApplication).inject(this)
        initUi()
    }

    override fun displayTopicName(name: String) {
        mTopicNameTextView.text = name
    }

    override fun displayNotes(notes: List<Note>) {
        mNoteAdapter.setNotes(notes)
    }
    override fun hideLoading() {
        mLoadingView?.visibility = GONE
    }

    override fun displayLoading() {
        mLoadingView?.visibility = VISIBLE
    }

    override fun clearNotes() {
        mNoteAdapter.setNotes(ArrayList())
    }

    override fun onClick(view: View?) {
        if (R.id.btn_new_note == view?.id) {
            mPresenter.onNewNoteClick()
        }
    }

    fun getPresenter() = mPresenter

    private fun initUi() {
        mNewNoteButton.setOnClickListener(this)
        mPresenter.attachView(this)
        initializeTopicSessionList(itemView.context)
    }

    private fun initializeTopicSessionList(context: Context) {
        mNotesList.layoutManager = LinearLayoutManager(context).apply {
            orientation = RecyclerView.VERTICAL
        }
        getItemMargin().apply {
            mNotesList.addItemDecoration(MarginItemDecoration(bottom = this))
        }

        mNoteAdapter = NoteAdapter()
        mNotesList.adapter = mNoteAdapter
    }

    private fun getItemMargin(): Int =
            itemView.context.resources.getDimensionPixelSize(R.dimen.margin_note_item)
}

class MarginItemDecoration constructor(
        private val start: Int = 0,
        private val end: Int = 0,
        private val bottom: Int = 0,
        private val top: Int = 0
): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State) {
        outRect.let {
            it.left = start
            it.right = end
            it.bottom = bottom
            it.top = top
        }
    }
}
