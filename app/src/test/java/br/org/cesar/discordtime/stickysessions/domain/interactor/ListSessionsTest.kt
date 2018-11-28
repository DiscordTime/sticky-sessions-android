package br.org.cesar.discordtime.stickysessions.domain.interactor

import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository
import br.org.cesar.discordtime.stickysessions.factory.DataFactory
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import java.util.*

class ListSessionsTest {

    private lateinit var mSessionRepositoryMock: SessionRepository

    @Before
    fun setUp() {
        mSessionRepositoryMock = mock()
    }

    @Test
    fun `should return all sessions avaliable`() {
        whenever(mSessionRepositoryMock.listSessions())
                .thenReturn(Single.create {
                    emitter ->
                    val session = Session()
                    session.id = DataFactory.randomString()
                    session.topics = Arrays.asList(DataFactory.randomString())
                    session.createdAt = DataFactory.randomString()

                    emitter.onSuccess(Arrays.asList(session))})

        val listSessions = ListSessions(mSessionRepositoryMock)
        val singleListSessions : Single<List<Session>> = listSessions.execute(null)

        val testObserver: TestObserver<List<Session>> = singleListSessions.test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()

        verify(mSessionRepositoryMock).listSessions()
    }

}