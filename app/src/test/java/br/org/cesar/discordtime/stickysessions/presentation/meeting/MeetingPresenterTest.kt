package br.org.cesar.discordtime.stickysessions.presentation.meeting

import br.org.cesar.discordtime.stickysessions.domain.model.Meeting
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase
import br.org.cesar.discordtime.stickysessions.factory.MeetingItemFactory.Factory.makeMeetingItem
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.junit.Before
import org.junit.Test

class MeetingPresenterTest {

    private lateinit var mockView: MeetingContract.View
    private lateinit var mockListMeetings:
            IObservableUseCase<Comparator<Meeting>, MutableList<Meeting>>
    private lateinit var meetingPresenter: MeetingPresenter

    @Before
    fun setUp() {
        mockView = mock()
        mockListMeetings = mock()
        meetingPresenter = MeetingPresenter(mockListMeetings, mock(), mock())
    }

    @Test
    fun `detachView should dispose use case`() {
        meetingPresenter.detachView()
        verify(mockListMeetings).dispose()
    }

    @Test
    fun `call onResume without attaching view does nothing`() {
        meetingPresenter.onResume()
        verifyZeroInteractions(mockListMeetings)
    }

    @Test
    fun `call enterOnMeeting without attaching view does nothing`() {
        meetingPresenter.enterOnMeeting(makeMeetingItem())
        verifyZeroInteractions(mockListMeetings)
    }
}