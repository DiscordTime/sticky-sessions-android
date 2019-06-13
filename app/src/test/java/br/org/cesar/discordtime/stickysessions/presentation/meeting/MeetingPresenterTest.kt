package br.org.cesar.discordtime.stickysessions.presentation.meeting

import br.org.cesar.discordtime.stickysessions.TestServerDispatcher
import br.org.cesar.discordtime.stickysessions.factory.MeetingItemFactory
import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerTestMeetingComponent
import br.org.cesar.discordtime.stickysessions.injectors.components.TestMeetingComponent
import br.org.cesar.discordtime.stickysessions.ui.ViewNames
import com.nhaarman.mockito_kotlin.*
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Captor
import javax.inject.Inject

class MeetingPresenterTest {

    @Inject
    lateinit var meetingPresenter: MeetingContract.Presenter

    private lateinit var mockView: MeetingContract.View
    private lateinit var webServer: MockWebServer

    @Captor
    private lateinit var captor: KArgumentCaptor<MutableList<MeetingItem>>

    @Before
    fun setUp() {
        mockView = mock()
        val component: TestMeetingComponent = DaggerTestMeetingComponent.builder().build()
        component.inject(this)

        webServer = MockWebServer()
        webServer.start(8080)

        captor = argumentCaptor()
    }

    @After
    fun tearDown() {
        webServer.shutdown()
    }

    @Test
    fun `onResume with attached view should call show meetings one time`() {
        webServer.setDispatcher(TestServerDispatcher().RequestDispatcher())
        meetingPresenter.attachView(mockView)
        meetingPresenter.onResume()
        verify(mockView, times(1)).showMeetings(any())
    }

    @Test
    fun `onResume with attached view should call show meetings with a positive number of meetings`() {
        webServer.setDispatcher(TestServerDispatcher().RequestDispatcher())
        meetingPresenter.attachView(mockView)
        meetingPresenter.onResume()
        verify(mockView).showMeetings(captor.capture())
        assertEquals(8, captor.firstValue.size)
    }

    @Test
    fun `onEnterMeeting should call goNext`(){
        webServer.setDispatcher(TestServerDispatcher().RequestDispatcher())
        whenever(mockView.getName()).thenReturn(ViewNames.MEETING_ACTIVITY)
        meetingPresenter.attachView(mockView)
        meetingPresenter.enterOnMeeting(MeetingItemFactory.makeMeetingItem())
        verify(mockView).goNext(any(), any())
    }

    @Test
    fun `should show error when request fails`(){
        webServer.enqueue(MockResponse().setResponseCode(404))
        meetingPresenter.attachView(mockView)
        meetingPresenter.onResume()
        verify(mockView, times(1)).showError(any())

    }

}