package br.org.cesar.discordtime.stickysessions.ui.meeting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.RecyclerView
import br.org.cesar.discordtime.stickysessions.R
import br.org.cesar.discordtime.stickysessions.presentation.meeting.MeetingItem
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.card_meeting_element.view.*

class MeetingItemAdapter(
        private val mContext: Context,
        private var mMeetingItems: List<MeetingItem>)
    : RecyclerView.Adapter<MeetingItemAdapter.MeetingViewHolder>() {

    var meetingItems: List<MeetingItem>
        get() = mMeetingItems
        set(value) {
            mMeetingItems = value
            notifyDataSetChanged()
        }

    private val styles = listOf(R.style.MeetingCardThemeRecent, R.style.MeetingCardThemeOld)
    private var clickSubject: PublishSubject<MeetingItem> = PublishSubject.create()
    lateinit var clickEvent: Observable<MeetingItem>


    constructor(context: Context): this(context, ArrayList<MeetingItem>()) {
        clickEvent = clickSubject
    }

    override fun getItemCount(): Int {
        return mMeetingItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (mMeetingItems[position].recent) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetingViewHolder {
        val view = LayoutInflater.from(ContextThemeWrapper(mContext, styles[viewType]))
                .inflate(R.layout.card_meeting_element, parent, false)
        return MeetingViewHolder(view)
    }

    override fun onBindViewHolder(holder: MeetingViewHolder, position: Int) {
        val meeting = mMeetingItems[position]
        holder.apply {
            if (meeting.recent) {
                section.text = mContext.getString(R.string.text_meeting_recents_section)
                progress.progress = 30
            } else {
                section.text = mContext.getString(R.string.text_meeting_older_section)
            }

            if (position != 0 && meeting.recent == mMeetingItems[position-1].recent) {
                section.visibility = View.GONE
            } else {
                section.visibility = View.VISIBLE
            }

            title.text = meeting.title
            location.text = meeting.location
            date.text = String.format(
                    mContext.getString(R.string.text_meeting_date_format),
                    meeting.date, meeting.time)
            description.text = meeting.description
            sessionsCount.text = mContext.resources.getQuantityString(
                    R.plurals.text_meeting_sessions_count_format,
                    meeting.numOfSessions, meeting.numOfSessions)

            participantsCount.text = mContext.resources.getQuantityString(
                    R.plurals.text_meeting_participants_count_format,
                    meeting.numOfParticipants, meeting.numOfParticipants)
        }
    }

    inner class MeetingViewHolder(itemView: View):
            RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val section = itemView.text_section!!
        val progress = itemView.progress_bar_meeting!!
        val title = itemView.text_title!!
        val location = itemView.text_location!!
        val date = itemView.text_date!!
        val description = itemView.text_description!!
        val sessionsCount = itemView.text_sessions_count!!
        val participantsCount = itemView.text_participants_count!!

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val realPosition = layoutPosition % mMeetingItems.size
            clickSubject.onNext(mMeetingItems[realPosition])
        }
    }
}