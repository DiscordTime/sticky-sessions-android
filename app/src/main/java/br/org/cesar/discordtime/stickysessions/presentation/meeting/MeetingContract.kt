package br.org.cesar.discordtime.stickysessions.presentation.meeting

interface MeetingContract {

    interface Presenter {
        fun attachView(view: MeetingContract.View)
        fun detachView()
        fun onResume()
    }

    interface View {
        fun startLoadingMeetings()
        fun stopLoadingMeetings()
        fun showError(message: String)
        fun showMeetings(meetings: MutableList<MeetingItem>)
    }
}