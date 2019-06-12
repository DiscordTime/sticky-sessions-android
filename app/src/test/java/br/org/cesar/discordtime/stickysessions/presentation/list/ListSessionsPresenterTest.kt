package br.org.cesar.discordtime.stickysessions.presentation.list

import br.org.cesar.discordtime.stickysessions.domain.model.Session
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase
import br.org.cesar.discordtime.stickysessions.logger.Logger
import br.org.cesar.discordtime.stickysessions.navigation.router.IRouter
import br.org.cesar.discordtime.stickysessions.navigation.router.Route
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.BundleWrapper
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundle
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundleFactory
import br.org.cesar.discordtime.stickysessions.ui.ViewNames
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableSingleObserver
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class ListSessionsPresenterTest {
    private lateinit var listSessionsPresenter : ListSessionsPresenter
    private lateinit var mockLisSessions: IObservableUseCase<String, List<Session>>
    private lateinit var mockRescheduleSession: IObservableUseCase<Session, Session>
    private lateinit var mockLogger: Logger
    private lateinit var mockIBundleFactory: IBundleFactory
    private lateinit var mockIRouter: IRouter
    private lateinit var mockView: ListSessionsContract.View
    private lateinit var captor: KArgumentCaptor<DisposableSingleObserver<List<Session>>>
    private lateinit var mListSession : List<Session>
    private lateinit var mIbundle : IBundle
    val validRoute = Route(
            ViewNames.LOBBY_ACTIVITY,
            IRouter.CREATED_SESSION,
            ViewNames.SESSION_ACTIVITY,
            false)

    @Before
    fun setUp() {
        mockLisSessions = mock()
        mockRescheduleSession = mock()
        mockIRouter = mock()
        mockLogger = mock()
        mockIBundleFactory = mock()
        mockView = mock()
        mIbundle = mock()
        listSessionsPresenter = ListSessionsPresenter(mockLisSessions, mockRescheduleSession,
                mockIRouter, mockLogger, mockIBundleFactory)

        captor = argumentCaptor<DisposableSingleObserver<List<Session>>>()

        mListSession = listOf<Session>(
                Session()
        )
    }

    @Test
    fun `should show error when meeting id is null`() {
        listSessionsPresenter.attachView(mockView)
        listSessionsPresenter.onLoad(null)
        verify(mockView).showError(any())
    }

    @Test
    fun `should show error when meeting id is empty`() {
        listSessionsPresenter.attachView(mockView)
        listSessionsPresenter.onLoad("")
        verify(mockView).showError(any())
    }

    @Test
    fun `should init observers when present start onLoad`() {
        listSessionsPresenter.attachView(mockView)
        listSessionsPresenter.onLoad(MEETING_ID)
        verify(mockLisSessions).execute(any(), any())
    }

    @Test
    fun `should dispose all observers after onPause`() {
        listSessionsPresenter.attachView(mockView)

        listSessionsPresenter.onLoad(MEETING_ID)
        verify(mockLisSessions).execute(captor.capture(), any())
        listSessionsPresenter.onStop()
        assertTrue(captor.firstValue.isDisposed)
    }

    @Test
    fun `should start loading data when present start load`() {
        listSessionsPresenter.attachView(mockView)
        listSessionsPresenter.onLoad(MEETING_ID)
        verify(mockView).startLoadingData()
    }

    @Test
    fun `should stop loading view when listSession returns onSucess`() {
        listSessionsPresenter.attachView(mockView)
        configureListSessionSuccess()
        verify(mockView).stopLoadingData()
    }

    @Test
    fun `should show sessions when listSession returns onSucess`() {
        listSessionsPresenter.attachView(mockView)
        configureListSessionSuccess()
        verify(mockView).showSessions(mListSession)
    }

    @Test
    fun `should stop loading when listSession returns onError`() {
        listSessionsPresenter.attachView(mockView)
        configureListSessionError()
        verify(mockView).stopLoadingData()
    }

    @Test
    fun `should show error when listSession returns onError`() {
        listSessionsPresenter.attachView(mockView)
        configureListSessionError()
        verify(mockView).showError(any())
    }

    @Test
    fun `should show retry option when listSession returns onError with IOException`() {
        listSessionsPresenter.attachView(mockView)
        configureListSessionError(IOException())
        verify(mockView).showRetryOption()
    }

    @Test
    fun `should call goNext when enterSession receive a valid session`() {
        configureBundle()
        listSessionsPresenter.attachView(mockView)
        listSessionsPresenter.enterOnSession(mListSession.first())
        verify(mockView).goNext(any(),any())
    }

    @Test
    fun `should showError when enterSession receive a invalid session`() {
        configureBundle()
        listSessionsPresenter.attachView(mockView)
        listSessionsPresenter.enterOnSession(null)
        verify(mockView).showError(any())
    }

    private fun configureListSessionSuccess() {
        listSessionsPresenter.onLoad(MEETING_ID)
        verify(mockLisSessions).execute(captor.capture(), any())
        captor.firstValue.onSuccess(mListSession)
    }

    private fun configureListSessionError() {
        configureListSessionError(Exception(""))
    }

    private fun configureListSessionError(e: Exception) {
        listSessionsPresenter.onLoad(MEETING_ID)
        verify(mockLisSessions).execute(captor.capture(), any())
        captor.firstValue.onError(e)
    }

    private fun configureBundle() {
        whenever(mockIBundleFactory.create()).thenReturn(BundleWrapper())
        whenever(mockView.name).thenReturn("ViewName")
        whenever(mockIRouter.getNext(any(),any())).thenReturn(validRoute)
    }

    companion object {
        private const val MEETING_ID = "meetingId"
    }


}