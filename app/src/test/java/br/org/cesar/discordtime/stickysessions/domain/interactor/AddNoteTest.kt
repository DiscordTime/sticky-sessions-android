package br.org.cesar.discordtime.stickysessions.domain.interactor

import br.org.cesar.discordtime.stickysessions.domain.model.Note
import br.org.cesar.discordtime.stickysessions.domain.model.Session
import br.org.cesar.discordtime.stickysessions.domain.repository.NoteRepository
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository
import br.org.cesar.discordtime.stickysessions.factory.DataFactory.Factory.randomString
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import java.util.*

class AddNoteTest {
    private lateinit var mNoteRepositoryMock: NoteRepository
    private lateinit var mSessionRepositoryMock: SessionRepository
    private lateinit var mNoteMock: Note


    @Before
    fun setUp() {
        mNoteRepositoryMock = mock()
        mSessionRepositoryMock = mock()
        mNoteMock = Note(randomString(), randomString(), "more", "1")
    }

    @Test
    fun `should call repository after addNote`() {
        configureSessionRepositoryMock("1", Arrays.asList("more", "less"))
        configureRepositoryMockAddNote()

        val addNote = AddNote(mNoteRepositoryMock, mSessionRepositoryMock)

        val testObserver:TestObserver<Note> = addNote.execute(mNoteMock).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()

        verify(mNoteRepositoryMock).addNote(mNoteMock)
    }

    @Test
    fun `should fail if note session id is different from current active session id`() {
        configureSessionRepositoryMock("2", Arrays.asList("more", "less"))
        configureRepositoryMockAddNote()

        val addNote = AddNote(mNoteRepositoryMock, mSessionRepositoryMock)
        val singleNote:Single<Note> = addNote.execute(mNoteMock)

        val testObserver:TestObserver<Note> = singleNote.test()
        testObserver.awaitTerminalEvent()
        testObserver.assertError(IllegalArgumentException::class.java)
    }

    private fun configureSessionRepositoryMock(idSession:String, topics:List<String>) {
        whenever(mSessionRepositoryMock.getSession(any()))
                .thenReturn(Single.create { emitter ->
                    val session = Session()
                    session.id = idSession
                    session.topics = topics

                    emitter.onSuccess(session)
                })
    }

    private fun configureRepositoryMockAddNote() {
        whenever(mNoteRepositoryMock.addNote(any()))
                .thenReturn(Single.create { emitter ->
                    emitter.onSuccess(mNoteMock)
                })
    }
}