package br.org.cesar.discordtime.stickysessions.presentation.lobby

import br.org.cesar.discordtime.stickysessions.domain.model.Session
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase
import br.org.cesar.discordtime.stickysessions.logger.Logger
import br.org.cesar.discordtime.stickysessions.navigation.router.IRouter
import br.org.cesar.discordtime.stickysessions.navigation.router.Route
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.BundleWrapper
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundle
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundleFactory
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Before
import org.junit.Test

class LobbyPresenterTest {

    private lateinit var lobbyPresenter: LobbyPresenter
    private lateinit var mockCreateSession: IObservableUseCase<SessionType, Session>
    private lateinit var mockIRouter: IRouter
    private lateinit var mockLogger: Logger
    private lateinit var mockIBundleFactory: IBundleFactory
    private lateinit var mockView: LobbyContract.View
    private lateinit var captor: KArgumentCaptor<DisposableSingleObserver<Session>>

    @Before
    fun setUp() {
        mockCreateSession = mock()
        mockIRouter = mock()
        mockLogger = mock()
        mockIBundleFactory = mock()
        mockView = mock()
        lobbyPresenter = LobbyPresenter(mockCreateSession, mockIRouter,
                mockLogger, mockIBundleFactory)

        captor = argumentCaptor<DisposableSingleObserver<Session>>()
    }

    @Test
    fun `should dispose all observers after detachView`() {
        lobbyPresenter.detachView()
        verify(mockCreateSession).dispose()
    }

    @Test
    fun `should call displayError in case of null session id`() {
        lobbyPresenter.attachView(mockView)
        lobbyPresenter.onEnterSession(null)
        verify(mockView).displayError(any())
    }

    @Test
    fun `should call displayError in case of empty session id`() {
        lobbyPresenter.attachView(mockView)
        lobbyPresenter.onEnterSession("")
        verify(mockView).displayError(any())
    }

    @Test
    fun `should call createSession use case with same type received`() {
        val type = LobbyContract.ActionType.CREATE_GAIN_N_PLEASURE_SESSION;
        val sessionType = SessionType.GAIN_PLEASURE;
        lobbyPresenter.attachView(mockView)
        lobbyPresenter.onClickSessionOption(type)
        verify(mockCreateSession).execute(any(), eq(sessionType))
    }

    @Test
    fun `should call goNext from view after successful create session`() {
        /* Configure internal calls */
        configureRouterGetNext(Route("from", "event"))
        configureBundleFactory(BundleWrapper())
        configureViewGetName("viewName")

        /* Call create session */
        val type = LobbyContract.ActionType.CREATE_GAIN_N_PLEASURE_SESSION
        val sessionType = SessionType.GAIN_PLEASURE
        lobbyPresenter.attachView(mockView)
        lobbyPresenter.onClickSessionOption(type)
        /* Capture observer to make sure we have a way to control response */
        verify(mockCreateSession).execute(captor.capture(), eq(sessionType))

        /* Test success flow */
        val session = Session()
        session.id = "1"
        captor.firstValue.onSuccess(session)
        verify(mockView).goNext(any(), any())
    }

    @Test
    fun `should show error on view after a failed create session`() {
        /* Call create session */
        val type = LobbyContract.ActionType.CREATE_GAIN_N_PLEASURE_SESSION
        val sessionType = SessionType.GAIN_PLEASURE
        lobbyPresenter.attachView(mockView)
        lobbyPresenter.onClickSessionOption(type)

        /* Capture observer to make sure we have a way to control response */
        verify(mockCreateSession).execute(captor.capture(), eq(sessionType))
        /* Test error flow */
        captor.firstValue.onError(Exception())

        verify(mockView).displayError(any())
    }

    private fun configureRouterGetNext(route: Route) {
        whenever(mockIRouter.getNext(any(), any())).thenReturn(route)
    }

    private fun configureBundleFactory(bundle: IBundle) {
        whenever(mockIBundleFactory.create()).thenReturn(bundle)
    }

    private fun configureViewGetName(name: String) {
        whenever(mockView.name).thenReturn(name)
    }
}