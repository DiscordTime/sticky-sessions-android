package br.org.cesar.discordtime.stickysessions.presentation.session

import br.org.cesar.discordtime.stickysessions.presentation.notes.INoteTopicDetail

interface SessionContract {
    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun onShareSession()
        fun onResume()
        fun currentSession(sessionId: String?)
    }

    interface View {
        fun shareSession(sessionId: String)
        fun displaySession(sessionDetail: List<INoteTopicDetail>)
        fun displayError(message: String?)
        fun startLoading()
        fun stopLoading()
        fun displayTitle(name: String?)
    }
}