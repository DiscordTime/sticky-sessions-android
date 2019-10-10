package br.org.cesar.discordtime.stickysessions.presentation.session

import br.org.cesar.discordtime.stickysessions.domain.model.Note
import br.org.cesar.discordtime.stickysessions.domain.model.NoteFilter
import br.org.cesar.discordtime.stickysessions.domain.model.Session
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase
import br.org.cesar.discordtime.stickysessions.logger.Logger
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Before
import org.junit.Test

class SessionPresenterTest {

    private lateinit var sessionPresenter: SessionPresenter
    private lateinit var mockEnterSession: IObservableUseCase<String, Session>
    private lateinit var mockAddNote: IObservableUseCase<Note, Note>
    private lateinit var mockListNotes: IObservableUseCase<NoteFilter, List<Note>>
    private lateinit var mockGetSavedUser: IObservableUseCase<Void, String>
    private lateinit var mockLogger: Logger

    private lateinit var mockView: SessionContract.View
    private lateinit var sessionCaptor: KArgumentCaptor<DisposableSingleObserver<Session>>
    private lateinit var noteCaptor: KArgumentCaptor<DisposableSingleObserver<Note>>
    private lateinit var listNoteCaptor: KArgumentCaptor<DisposableSingleObserver<List<Note>>>
    private lateinit var booleanCaptor: KArgumentCaptor<DisposableSingleObserver<Boolean>>
    private lateinit var stringCaptor: KArgumentCaptor<DisposableSingleObserver<String>>

    private lateinit var mCurrentUser: String
    private lateinit var mSession: Session

    private lateinit var mockRemoveNote:  IObservableUseCase<Note, Boolean>

    @Before
    fun setUp() {
        mockEnterSession = mock()
        mockAddNote = mock()
        mockRemoveNote = mock()
        mockListNotes = mock()
        mockGetSavedUser = mock()
        mockLogger = mock()
        mockView = mock()
        sessionPresenter = SessionPresenter(mockEnterSession, mockAddNote, mockRemoveNote,
                mockListNotes, mockGetSavedUser, mockLogger)

        sessionCaptor = argumentCaptor<DisposableSingleObserver<Session>>()
        noteCaptor = argumentCaptor<DisposableSingleObserver<Note>>()
        listNoteCaptor = argumentCaptor<DisposableSingleObserver<List<Note>>>()
        booleanCaptor = argumentCaptor<DisposableSingleObserver<Boolean>>()
        stringCaptor = argumentCaptor<DisposableSingleObserver<String>>()

        mCurrentUser = "User"
        mSession = Session()
        mSession.id = "1"
        mSession.topics = listOf("start","a", "b", "c")
    }

    @Test
    fun `should dispose all observers after detachView`() {
        sessionPresenter.detachView()
        verify(mockEnterSession).dispose()
        verify(mockAddNote).dispose()
        verify(mockListNotes).dispose()
        verify(mockGetSavedUser).dispose()
    }

    @Test
    fun `should fails if has no user during onResume`(){
        sessionPresenter.attachView(mockView)
        sessionPresenter.onResume()
        verify(mockGetSavedUser).execute(stringCaptor.capture(), isNull())

        stringCaptor.firstValue.onError(Exception(""))
        verify(mockView).stopLoading()
        // TODO: Check if goes back to login?
    }

    @Test
    fun `should start loading all notes when call onResume `(){
        sessionPresenter.attachView(mockView)
        sessionPresenter.onResume()
        verify(mockView).startLoading()
    }

    @Test
    fun `should start loading a note when we add a Note `(){
        configureActiveSessionSuccess()
        val note = Note("doing tests", "Author", "start", mSession.id)
        sessionPresenter.addNewNote(note.topic, note.description)
        verify(mockView).startLoadingNote()
    }

    @Test
    fun `should stop loading when the server added a Note `(){
        configureActiveSessionSuccess()
        val note = Note("doing tests", "Author", "start", mSession.id)
        sessionPresenter.addNewNote(note.topic, note.description)
        verify(mockAddNote).execute(noteCaptor.capture(), any())
        noteCaptor.firstValue.onSuccess(note)
        verify(mockView).stopLoadingNote();
        verify(mockView).addNoteToNoteList(note);
    }

    @Test
    fun `should stop loading after failed enter session`(){
        sessionPresenter.attachView(mockView)
        configureGetSavedUserSuccess()
        configureEnterSessionError()
        verify(mockView).stopLoading()
        verify(mockView).displayError(any())
    }

    @Test
    fun `should stop loading when listNotesForCurrentSession success `(){
        configureActiveSessionSuccess()
        verify(mockListNotes).execute(listNoteCaptor.capture(), any())

        val notes = listOf(
                Note("d", "u", "a", "1"),
                Note("d", "u", "b", "1"))
        listNoteCaptor.firstValue.onSuccess(notes)
        verify(mockView).displayNotes(notes)
        verify(mockView).stopLoading()
    }

    @Test
    fun `should stop loading when listNotesForCurrentSession error `(){
        configureActiveSessionSuccess()
        verify(mockListNotes).execute(listNoteCaptor.capture(), any())

        listNoteCaptor.firstValue.onError(Exception())
        verify(mockView).displayErrorInvalidNotes()
        verify(mockView).stopLoading()
    }


    @Test
    fun `should call enter session if has user during onResume`(){
        sessionPresenter.attachView(mockView)
        configureGetSavedUserSuccess()
        verify(mockEnterSession).execute(any(), any())
    }

    @Test
    fun `should display session after successful enter session`(){
        configureActiveSessionSuccess()
        verify(mockView).displaySession()
    }


    @Test
    fun `should not call share if session is invalid`() {
        sessionPresenter.attachView(mockView)
        sessionPresenter.onShareSession()
        verifyZeroInteractions(mockView)
    }

    @Test
    fun `should call share if session is valid`() {
        configureActiveSessionSuccess()
        sessionPresenter.onShareSession()
        verify(mockView).shareSession(mSession.id)
    }

    @Test
    fun `should not call display note form if session is invalid`() {
        sessionPresenter.attachView(mockView)
        sessionPresenter.onAddNoteClicked()
        verifyZeroInteractions(mockView)
    }

    @Test
    fun `should call display note form if session is valid`() {
        configureActiveSessionSuccess()
        sessionPresenter.onAddNoteClicked()
        verify(mockView).displayAddNoteDialog(mSession.topics)
    }

    @Test
    fun `should call display notes after successful list notes`() {
        configureActiveSessionSuccess()

        verify(mockListNotes).execute(listNoteCaptor.capture(), any())

        val notes = listOf(
                Note("d", "u", "a", "1"),
                Note("d", "u", "b", "1"))
        listNoteCaptor.firstValue.onSuccess(notes)
        verify(mockView).displayNotes(notes)
    }

    @Test
    fun `should call error after failed list notes`() {
        configureActiveSessionSuccess()
        verify(mockListNotes).execute(listNoteCaptor.capture(), any())

        listNoteCaptor.firstValue.onError(Exception())
        verify(mockView).displayErrorInvalidNotes()
    }

    @Test
    fun `should display note content on note click`(){
        val note = Note("","","","")
        sessionPresenter.attachView(mockView)
        sessionPresenter.onNoteWidgetClicked(note)
        verify(mockView).displayNoteContent(note)
    }

    @Test
    fun `should not display note content if note is null`(){
        sessionPresenter.attachView(mockView)
        sessionPresenter.onNoteWidgetClicked(null)
        verifyZeroInteractions(mockView)
    }

    @Test
    fun `should not display add note dialog on click if no session`() {
        sessionPresenter.attachView(mockView)
        sessionPresenter.onAddNoteClicked()
        verifyZeroInteractions(mockView)
    }

    @Test
    fun `should display add note dialog on click`(){
        configureActiveSessionSuccess()
        sessionPresenter.onAddNoteClicked()
        verify(mockView).displayAddNoteDialog(mSession.topics)
    }

    @Test
    fun `should do nothing when add note is called without session`() {
        sessionPresenter.attachView(mockView)
        sessionPresenter.addNewNote("", "")
        verifyZeroInteractions(mockView)
    }

    @Test
    fun `should show note after successful add new note`() {
        configureActiveSessionSuccess()
        val note = Note("d", "Author", "a", mSession.id)
        sessionPresenter.addNewNote(note.topic, note.description)
        verify(mockAddNote).execute(noteCaptor.capture(), any())

        noteCaptor.firstValue.onSuccess(note)
        verify(mockView).addNoteToNoteList(note);
    }

    @Test
    fun `should show error after failed add new note`() {
        configureActiveSessionSuccess()

        sessionPresenter.addNewNote("a", "d")
        verify(mockAddNote).execute(noteCaptor.capture(), any())

        noteCaptor.firstValue.onError(Exception(""))
        verify(mockView).displayError(any())
    }

    private fun configureGetSavedUserSuccess() {
        sessionPresenter.currentSession(mSession.id)
        sessionPresenter.onResume()
        verify(mockGetSavedUser).execute(stringCaptor.capture(), isNull())
        stringCaptor.firstValue.onSuccess(mCurrentUser)
    }

    private fun configureEnterSessionError() {
        configureEnterSession()
        sessionCaptor.firstValue.onError(Exception(""))
    }

    private fun configureEnterSessionSuccess() {
        configureEnterSession()
        sessionCaptor.firstValue.onSuccess(mSession)
    }

    private fun configureEnterSession() {
        verify(mockEnterSession).execute(sessionCaptor.capture(), eq(mSession.id))
    }

    private fun configureActiveSessionSuccess() {
        sessionPresenter.attachView(mockView)
        configureGetSavedUserSuccess()
        configureEnterSessionSuccess()
    }

}
