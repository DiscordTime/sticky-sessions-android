package br.org.cesar.discordtime.stickysessions.presentation.meeting

import br.org.cesar.discordtime.stickysessions.domain.model.Meeting
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase
import io.reactivex.observers.DisposableSingleObserver
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator

class MeetingPresenter(
        private val listMeetings: IObservableUseCase<Comparator<Meeting>, MutableList<Meeting>>)
    : MeetingContract.Presenter {

    var mView: MeetingContract.View? = null

    override fun attachView(view: MeetingContract.View) {
        mView = view
    }
    override fun detachView() {
        mView = null
        listMeetings.dispose()
    }
    override fun onResume() {
        mView?.let { view ->
            view.startLoadingMeetings()
            listMeetings.execute(
                    MeetingsObserver(),
                    MeetingsComparator())
        }
    }

    private inner class MeetingsComparator: Comparator<Meeting> {
        // descending order m2 > m1
        override fun compare(m1: Meeting, m2: Meeting): Int {
            return m2.date.compareTo(m1.date)
        }
    }

    private inner class MeetingsObserver: DisposableSingleObserver<MutableList<Meeting>>() {
        override fun onSuccess(meetings: MutableList<Meeting>) {
            mView?.run {
                showMeetings(
                        meetings.map {
                            mapFromDomain(it).apply {
                                recent = isARecentMeeting(it)
                            }
                        } as MutableList<MeetingItem>
                )
                stopLoadingMeetings()
            }
        }

        override fun onError(e: Throwable) {
            mView?.run {
                stopLoadingMeetings()
                showError(e.localizedMessage)
            }
        }
    }

    private fun isARecentMeeting(meeting: Meeting): Boolean {
            val meetingCalendar = Calendar.getInstance()
            val today = Calendar.getInstance()
            meetingCalendar.time = meeting.date
            return meetingCalendar.get(Calendar.DAY_OF_YEAR) >= today.get(Calendar.DAY_OF_YEAR)
                    && meetingCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)
    }

    private fun mapFromDomain(meeting: Meeting): MeetingItem {
        return MeetingItem(
                id = meeting.id,
                title = meeting.title,
                description = meeting.description,
                location = meeting.location,
                date = SimpleDateFormat("dd/MM", Locale.US).format(meeting.date),
                time = SimpleDateFormat("HH:mm", Locale.US).format(meeting.date),
                numOfSessions = meeting.sessions.size,
                numOfParticipants = meeting.participants.size
        )
    }
}