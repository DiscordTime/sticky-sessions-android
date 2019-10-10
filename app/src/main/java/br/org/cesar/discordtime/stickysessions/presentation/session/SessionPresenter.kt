package br.org.cesar.discordtime.stickysessions.presentation.session

import br.org.cesar.discordtime.stickysessions.domain.model.ISessionDetail
import br.org.cesar.discordtime.stickysessions.domain.model.Session
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase
import br.org.cesar.discordtime.stickysessions.logger.Logger
import br.org.cesar.discordtime.stickysessions.presentation.notes.INoteTopicDetail
import br.org.cesar.discordtime.stickysessions.presentation.notes.NoteTopicDetail
import io.reactivex.observers.DisposableSingleObserver

class SessionPresenter(
        private val mEnterSession: IObservableUseCase<String, Session>,
        private val mGetSavedUser: IObservableUseCase<Void, String>,
        private val mLog: Logger
) : SessionContract.Presenter {
    companion object {
        @JvmStatic private val TAG = "SessionPresenter"
    }

    private var mView: SessionContract.View? = null
    private var mActiveSession: Session? = null
    private var mSessionId: String? = null
    private var mCurrentUser: String? = null

    override fun attachView(view: SessionContract.View) {
        mView = view
    }

    override fun onResume() {
        mView?.startLoading()
        mGetSavedUser.execute(object: DisposableSingleObserver<String>() {
            override fun onSuccess(userName: String) {
                mCurrentUser = userName
                onEnterSession()
            }

            override fun onError(e: Throwable) {
                mView?.stopLoading()
                // TODO: Go back to login?
            }
        }, null)
    }

    override fun currentSession(sessionId: String?) {
        mSessionId = sessionId
    }

    override fun detachView() {
        mView = null
        disposeObservers()
    }

    override fun onShareSession() {
        if (isSessionActive()) {
            mView?.shareSession(mActiveSession?.id ?: "")
        }
    }

    private fun onEnterSession() {
        mLog.d(TAG, "onEnterSession : $mSessionId")
        mEnterSession.execute(object : DisposableSingleObserver<Session>() {
            override fun onSuccess(session: Session) {
                mActiveSession = session

                mLog.d(TAG, "displaySession: " + mActiveSession?.getId())
                mView?.stopLoading()
                mView?.displayTitle(mActiveSession?.name)
                val notes = getNoteTopicDetailList(mActiveSession)
                if (notes?.isNotEmpty() == true) mView?.displaySession(notes)
                // TODO:  else show Empty notes UI
            }

            override fun onError(e: Throwable) {
                // Todo : handle errors gracefully
                mView?.stopLoading()
                mView?.displayError(e.message)
            }
        }, mSessionId)
    }

    private fun disposeObservers() {
        mEnterSession.dispose()
        mGetSavedUser.dispose()
    }

    private fun isSessionActive(): Boolean = mActiveSession?.id != null

    private fun getNoteTopicDetailList(sessionDetail: ISessionDetail?): List<INoteTopicDetail>? =
            sessionDetail?.topics?.map {
                topicName -> NoteTopicDetail(topicName, mSessionId, mCurrentUser) }
}