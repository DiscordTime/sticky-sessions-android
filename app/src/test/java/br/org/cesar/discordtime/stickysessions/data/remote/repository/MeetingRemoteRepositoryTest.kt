package br.org.cesar.discordtime.stickysessions.data.remote.repository

import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository
import br.org.cesar.discordtime.stickysessions.factory.SessionFactory
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MeetingRemoteRepositoryTest {
    private lateinit var mSessionRepositoryMock: SessionRepository
    private lateinit var mMeetingRemoteRepository: MeetingRemoteRepository

    @Before
    fun setUp() {
        mSessionRepositoryMock = mock()
        mMeetingRemoteRepository = MeetingRemoteRepository(mSessionRepositoryMock)
    }

    @Test
    fun `should group sessions into meetings`() {
        whenever(mSessionRepositoryMock.listSessions()).thenReturn(
                    Single.just(listOf(
                        SessionFactory.makeSession(4),
                        SessionFactory.makeSession(5, "21.03.2019"),
                        SessionFactory.makeSession(5),
                        SessionFactory.makeSession(4, "21.03.2019"),
                        SessionFactory.makeSession(3, "21.03.2019"),
                        SessionFactory.makeSession(3)
                    ))
                )

        val meetings = mMeetingRemoteRepository.listMeetings().blockingGet()
        assertEquals(2, meetings.size)
        assertEquals(3, meetings[0].sessions.size)
        assertEquals(3, meetings[1].sessions.size)
    }

    @Test
    fun `should filter null or invalid sessions`() {
        whenever(mSessionRepositoryMock.listSessions()).thenReturn(
                Single.just(listOf(
                        null,
                        SessionFactory.makeSession(5, ""),
                        SessionFactory.makeSession(5, "21.03.2019"),
                        null,
                        SessionFactory.makeSession(3, "21.03.2019"),
                        SessionFactory.makeSession(5, "")
                ))
        )

        val meetings = mMeetingRemoteRepository.listMeetings().blockingGet()
        assertEquals(1, meetings.size)
        assertEquals(2, meetings[0].sessions.size)
        assertEquals("21.03.2019", meetings[0].id)
    }
}