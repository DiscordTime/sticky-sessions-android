package br.org.cesar.discordtime.stickysessions.domain.interactor

import br.org.cesar.discordtime.stickysessions.domain.model.Session
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import java.util.*

class CreateSessionTest {

    private lateinit var createSession: CreateSession
    private lateinit var mockSessionRepository: SessionRepository
    private lateinit var startfishList: List<String>
    private lateinit var gainPleasureList: List<String>

    @Before
    fun setUp() {
        mockSessionRepository = mock()
        createSession = CreateSession(mockSessionRepository)
        startfishList = Arrays.asList("Less", "More", "Start", "Stop", "Keep")
        gainPleasureList = Arrays.asList("Loss & Pain", "Loss & Pleasure",
                "Gain & Pain", "Gain & Pleasure")
    }

    @Test
    fun `should call repository with starfish list`() {
        createSession.execute(SessionType.STARFISH)
        verify(mockSessionRepository).create(startfishList)
    }

    @Test
    fun `should call repository with gain & pleasure list`() {
        createSession.execute(SessionType.GAIN_PLEASURE)
        verify(mockSessionRepository).create(gainPleasureList)
    }

    @Test
    fun `should receive one onComplete event on starfish case`() {
        val session = Session()
        stubSessionRepositoryCreate(Single.just(session))
        val testObserver = createSession.execute(SessionType.STARFISH).test()
        testObserver.assertComplete()
    }

    @Test
    fun `should receive one onComplete event on gain & pleasure case`() {
        val session = Session()
        stubSessionRepositoryCreate(Single.just(session))
        val testObserver = createSession.execute(SessionType.GAIN_PLEASURE).test()
        testObserver.assertComplete()
    }

    @Test
    fun `should return data from repository on starfish case`() {
        val session = Session()
        stubSessionRepositoryCreate(Single.just(session))
        val testObserver = createSession.execute(SessionType.STARFISH).test()
        testObserver.assertValue(session)
    }

    @Test
    fun `should return data from repository on gain & pleasure case`() {
        val session = Session()
        stubSessionRepositoryCreate(Single.just(session))
        val testObserver = createSession.execute(SessionType.GAIN_PLEASURE).test()
        testObserver.assertValue(session)
    }

    private fun stubSessionRepositoryCreate(single: Single<Session>) {
        whenever(mockSessionRepository.create(any()))
                .thenReturn(single)
    }
}