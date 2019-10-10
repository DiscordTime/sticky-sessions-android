package br.org.cesar.discordtime.stickysessions.ui.notes;

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import br.org.cesar.discordtime.stickysessions.R
import br.org.cesar.discordtime.stickysessions.app.StickySessionApplication
import br.org.cesar.discordtime.stickysessions.presentation.notes.INoteTopicDetail
import br.org.cesar.discordtime.stickysessions.presentation.session.SessionContract
import br.org.cesar.discordtime.stickysessions.ui.ExtraNames
import br.org.cesar.discordtime.stickysessions.ui.notes.adapters.NotesByNotesAdapter
import javax.inject.Inject

class NotesActivity : AppCompatActivity(), SessionContract.View {
    companion object {
        const val TAG = "SessionActivity"
    }

    private val mRecyclerView by lazy { findViewById<RecyclerView>(R.id.user_topic_session) }
    private val mProgressBar by lazy { findViewById<ProgressBar>(R.id.progress_bar) }
    private val mToolbarTitleTextView by lazy { findViewById<TextView>(R.id.toolbar_title) }
    private lateinit var mNotesByTopicAdapter: NotesByNotesAdapter

    @Inject
    lateinit var mPresenter: SessionContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session)

        (applicationContext as StickySessionApplication).inject(this)

        bindView()
        configureSession()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        configureSession()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.session_menu, menu)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    /* SessionContract.View overrides --- */

    override fun displayError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun startLoading() {
        mRecyclerView.visibility = View.INVISIBLE
        mProgressBar.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        mRecyclerView.visibility = View.VISIBLE
        mProgressBar.visibility = View.GONE
    }

    override fun displayTitle(name: String?) {
        mToolbarTitleTextView.text = name
    }

    override fun shareSession(sessionId: String){
        // TODO: move this code to a util class
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, String.format(getString(R.string.share_session, sessionId)))
        })
    }

    override fun displaySession(sessionDetail: List<INoteTopicDetail>) {
        mNotesByTopicAdapter.replaceData(sessionDetail)
    }

    private fun bindView() {
        configureToolbar()

        initializeTopicSessionList()
        mPresenter.attachView(this)
    }

    private fun configureToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_back)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun initializeTopicSessionList() {
        mRecyclerView.layoutManager = LinearLayoutManager(this).apply {
            orientation = RecyclerView.HORIZONTAL
        }
        // TODO: Check if this is necessary
//        mRecyclerView.itemAnimator = ItemAnimator(this);

        // TODO: Check if this is necessary
//        LinearSnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(mRecyclerView);

        mNotesByTopicAdapter = NotesByNotesAdapter()
        mRecyclerView.adapter = mNotesByTopicAdapter
    }

    private fun configureSession() {
        //Enter in a session by link
        if (Intent.ACTION_VIEW == intent.action) {
            val uri = intent.data
            if (uri != null) {
                val sessionId = uri.getQueryParameter(ExtraNames.SESSION_ID)
                Log.d(TAG, "sessionId $sessionId")
                mPresenter.currentSession(sessionId)
            } else {
                //TODO error message to null data
                Log.d(TAG, "null sessionId.")
            }
        //Enter in a session by Lobby
        } else if(!TextUtils.isEmpty(intent.getStringExtra(ExtraNames.SESSION_ID))) {
            val sessionId = intent.getStringExtra(ExtraNames.SESSION_ID)
            Log.d(TAG, "sessionId $sessionId")
            mPresenter.currentSession(sessionId)
        }
    }
}
