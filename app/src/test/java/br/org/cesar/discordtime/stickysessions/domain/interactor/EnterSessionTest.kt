package br.org.cesar.discordtime.stickysessions.domain.interactor

import br.org.cesar.discordtime.stickysessions.domain.model.Session
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class EnterSessionTest {

    private lateinit var enterSession: EnterSession
    private lateinit var mockSessionRepository: SessionRepository

    @Before
    fun setUp() {
        mockSessionRepository = mock()
        enterSession = EnterSession(mockSessionRepository)
    }

    @Test
    fun `should call repository with sessionId`() {
        var sessionId: String = "abc"
        enterSession.execute(sessionId)
        verify(mockSessionRepository).getSession(sessionId)
    }

    @Test
    fun `should receive one onComplete event on execute a valid sessionId`() {
        var sessionId: String = "abc"
        val session = Session()
        stubSessionRepositoryCreate(Single.just(session))
        val testObserver = enterSession.execute(sessionId).test()
        testObserver.assertComplete()
    }

    @Test
    fun `should receive one IllegalArgumentException when valid session is empty`() {
        var sessionId: String = ""
        val session = Session()
        stubSessionRepositoryCreate(Single.just(session))
        val testObserver = enterSession.execute(sessionId).test()
        testObserver.assertError(IllegalArgumentException::class.java)
    }


    private fun stubSessionRepositoryCreate(single: Single<Session>) {
        whenever(mockSessionRepository.getSession(any()))
                .thenReturn(single)
    }
}
