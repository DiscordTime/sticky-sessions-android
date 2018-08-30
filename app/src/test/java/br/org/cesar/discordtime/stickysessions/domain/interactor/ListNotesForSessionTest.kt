package br.org.cesar.discordtime.stickysessions.domain.interactor

import br.org.cesar.discordtime.stickysessions.domain.model.Note
import br.org.cesar.discordtime.stickysessions.domain.model.Session
import br.org.cesar.discordtime.stickysessions.domain.repository.NoteRepository
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository
import br.org.cesar.discordtime.stickysessions.factory.DataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import java.util.*

class ListNotesForSessionTest {

    private lateinit var mNoteRepositoryMock: NoteRepository
    private lateinit var mSessionRepositoryMock: SessionRepository

    @Before
    fun setUp() {
        mNoteRepositoryMock = mock()
        mSessionRepositoryMock = mock()
    }

    @Test
    fun `invalid session id should throw an exception`(){
        val sessionId = "invalid_session_id"

        whenever(mSessionRepositoryMock.getSession(sessionId))
                .thenReturn(Single.create { emitter ->
                    emitter.onError(Exception("invalid_session_id break the flow"))
                })

        val listNotes = ListNotesForSession(mNoteRepositoryMock, mSessionRepositoryMock)
        val singleListNote: Single <List<Note>> = listNotes.execute(sessionId)

        val testObserver: TestObserver<List<Note>> = singleListNote.test()
        testObserver.awaitTerminalEvent()
        testObserver.assertError(Exception::class.java)
    }

    @Test
    fun `valid session id should return value`() {
        val sessionId = "session"

        whenever(mSessionRepositoryMock.getSession(sessionId))
                .thenReturn(Single.create { emitter ->
                    emitter.onSuccess(Session())
                })

        whenever(mNoteRepositoryMock.listNotesForSession(any()))
                .thenReturn(Single.create {
                    emitter ->
                        val note = Note(
                                DataFactory.randomString(),
                                DataFactory.randomString(),
                                DataFactory.randomString(),
                                sessionId)

                        emitter.onSuccess(Arrays.asList(note))
                })

        val listNotes = ListNotesForSession(mNoteRepositoryMock, mSessionRepositoryMock)
        val singleListNote: Single <List<Note>> = listNotes.execute(sessionId)

        val testObserver: TestObserver<List<Note>> = singleListNote.test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()

        verify(mSessionRepositoryMock).getSession(sessionId)
        verify(mNoteRepositoryMock).listNotesForSession(sessionId)
    }
}