package br.org.cesar.discordtime.stickysessions.domain.interactor

import br.org.cesar.discordtime.stickysessions.domain.model.Meeting
import br.org.cesar.discordtime.stickysessions.domain.model.Session
import br.org.cesar.discordtime.stickysessions.domain.repository.MeetingRepository
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository
import br.org.cesar.discordtime.stickysessions.factory.MeetingFactory
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ListMeetingsTest {

    private lateinit var listMeetings: ListMeetings
    private lateinit var mockMeetingRepository: MeetingRepository

    @Before
    fun setUp() {
        mockMeetingRepository = mock()
        listMeetings = ListMeetings(mockMeetingRepository)
    }

    @Test
    fun `should return meetings sorted by date`() {
        whenever(mockMeetingRepository.listMeetings()).thenReturn(
            Single.just(mutableListOf(
                MeetingFactory.makeMeeting(2,5,1),
                MeetingFactory.makeMeeting(2,7,0),
                MeetingFactory.makeMeeting(2,8,3),
                MeetingFactory.makeMeeting(2,9,-1),
                MeetingFactory.makeMeeting(2,4,2)
            ))
        )

        val meetings = listMeetings.execute(MeetingsComparator()).blockingGet()
        assertEquals(5, meetings.size)
        assertEquals(9, meetings[0].participants.size)
        assertEquals(7, meetings[1].participants.size)
        assertEquals(5, meetings[2].participants.size)
        assertEquals(4, meetings[3].participants.size)
        assertEquals(8, meetings[4].participants.size)
    }

    private inner class MeetingsComparator: Comparator<Meeting> {
        override fun compare(m1: Meeting, m2: Meeting): Int {
            return m1.date.compareTo(m2.date)
        }
    }
}