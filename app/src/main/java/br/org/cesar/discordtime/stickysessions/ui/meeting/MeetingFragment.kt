package br.org.cesar.discordtime.stickysessions.ui.meeting

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.org.cesar.discordtime.stickysessions.R
import br.org.cesar.discordtime.stickysessions.app.StickySessionApplication
import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidViewNameException
import br.org.cesar.discordtime.stickysessions.navigation.router.Route
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundle
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IViewStarter
import br.org.cesar.discordtime.stickysessions.presentation.meeting.MeetingContract
import br.org.cesar.discordtime.stickysessions.presentation.meeting.MeetingItem
import br.org.cesar.discordtime.stickysessions.ui.ViewNames
import javax.inject.Inject

class MeetingFragment : Fragment(), MeetingContract.View {

    @Inject
    lateinit var mPresenter: MeetingContract.Presenter
    @Inject
    lateinit var mViewStarter: IViewStarter
    lateinit var mProgressBar: ProgressBar
    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: MeetingItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as StickySessionApplication).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_meeting, container, false)

        mProgressBar = rootView.findViewById(R.id.progressbar)
        configureRecycleView(rootView)
        return rootView
    }

    @SuppressLint("CheckResult")
    private fun configureRecycleView(rootView: View) {
        mAdapter = MeetingItemAdapter(rootView.context)
        mRecyclerView = rootView.findViewById(R.id.recycler_view_meetings)
        mRecyclerView.apply {
            layoutManager = LinearLayoutManager(rootView.context)
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
        mViewStarter.goNext(activity, route.to, route.shouldClearStack, bundle)
    }

    companion object {
        const val TAG = "MeetingFragment"
    }
}
