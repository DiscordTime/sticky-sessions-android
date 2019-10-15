package br.org.cesar.discordtime.stickysessions.presentation.meeting

import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidViewNameException
import br.org.cesar.discordtime.stickysessions.navigation.router.Route
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundle

interface MeetingContract {

    interface Presenter {
        fun attachView(view: MeetingContract.View)
        fun detachView()
        fun onResume()
        fun enterOnMeeting(meetingItem: MeetingItem)
        fun onRetryNetworkClick()
    }

    interface View {
        fun startLoadingMeetings()
        fun stopLoadingMeetings()
        fun showError(message: String)
        fun showMeetings(meetings: MutableList<MeetingItem>)
        fun getName(): String
        @Throws(InvalidViewNameException::class)
        fun goNext(route: Route, bundle: IBundle)
        fun isMeetingsEmpty(): Boolean
        fun showNetworkError()
        fun hideNetworkError()
        fun showNetworkErrorIcon()
    }
}