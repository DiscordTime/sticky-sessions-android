package br.org.cesar.discordtime.stickysessions.ui.meeting

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import br.org.cesar.discordtime.stickysessions.R
import br.org.cesar.discordtime.stickysessions.app.StickySessionApplication
import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidViewNameException
import br.org.cesar.discordtime.stickysessions.navigation.router.Route
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundle
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IViewStarter
import br.org.cesar.discordtime.stickysessions.presentation.meeting.MeetingContract
import br.org.cesar.discordtime.stickysessions.presentation.meeting.MeetingItem
import br.org.cesar.discordtime.stickysessions.ui.ViewNames
import kotlinx.android.synthetic.main.activity_meeting.*
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_meeting.recycler_view_meetings as mRecyclerView

class MeetingActivity : AppCompatActivity(), MeetingContract.View {

    @Inject
    lateinit var mPresenter: MeetingContract.Presenter
    @Inject
    lateinit var mViewStarter: IViewStarter
    lateinit var mProgressBar: ProgressBar
    lateinit var mAdapter: MeetingItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meeting)
        (application as StickySessionApplication).inject(this)

        mProgressBar = findViewById(R.id.progressbar)
        configureToolbar()
        configureRecycleView()
    }

    private fun configureToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toolbarTitle = findViewById<TextView>(R.id.toolbar_title)
        toolbarTitle.setText(R.string.toolbar_title_meetings)
    }

    @SuppressLint("CheckResult")
    private fun configureRecycleView() {
        mAdapter = MeetingItemAdapter(this)
        mRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MeetingActivity)
            adapter = mAdapter
        }
        mAdapter.clickEvent.subscribe {
            meetingItem -> mPresenter.enterOnMeeting(meetingItem)
        }
    }

    override fun onStart() {
        super.onStart()
        mPresenter.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun startLoadingMeetings() {
        mProgressBar.visibility = View.VISIBLE
        mRecyclerView.visibility = View.INVISIBLE
    }

    override fun stopLoadingMeetings() {
        mProgressBar.visibility = View.INVISIBLE
        mRecyclerView.visibility = View.VISIBLE
    }

    override fun showError(message: String) {
        Log.e(TAG, message)
    }

    override fun showMeetings(meetings: MutableList<MeetingItem>) {
        mAdapter.meetingItems = meetings
    }

    override fun getName(): String {
        return ViewNames.MEETING_ACTIVITY
    }

    @Throws(InvalidViewNameException::class)
    override fun goNext(route: Route, bundle: IBundle) {
        mViewStarter.goNext(this, route.to, route.shouldClearStack, bundle)
    }

    companion object {
        const val TAG = "MeetingActivity"
    }
}
