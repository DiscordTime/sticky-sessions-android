package br.org.cesar.discordtime.stickysessions.data.service

import br.org.cesar.discordtime.stickysessions.data.remote.model.NoteRemote
import br.org.cesar.discordtime.stickysessions.data.remote.service.NoteService
import br.org.cesar.discordtime.stickysessions.data.remote.service.RemoteServiceFactory
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.mock
import okhttp3.Interceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class NoteServiceTest {

    private lateinit var noteService: NoteService
    private lateinit var mWebServerMock: MockWebServer
    private lateinit var mNoteMock:NoteRemote
    private lateinit var mSourceJsonContent:String
    private lateinit var mSourceInvalidJsonContent:String

    @Before
    fun setUp() {
        mWebServerMock = MockWebServer()
        val list = ArrayList<Interceptor>()
        noteService = RemoteServiceFactory<NoteService>()
                .makeRemoteService(
                        mock(),
                        mWebServerMock.url("/").toString(),
                        NoteService::class.java,
                        list
                )

        mSourceJsonContent =
                javaClass.classLoader?.getResource("note_ok_response.json")?.readText()!!

        mSourceInvalidJsonContent =
                javaClass.classLoader?.getResource("note_invalid_response.json")?.readText()!!

        mNoteMock = createNoteMock()
    }

    private fun createNoteMock(): NoteRemote {
        return Gson().fromJson<NoteRemote>(mSourceJsonContent, NoteRemote::class.java)
    }

    @Test
    fun `after addNote on Server, the new data should be returned`() {
        mWebServerMock.enqueue(createValidSessionResponse())
        val testObserver = noteService.addNote(mNoteMock).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val noteRemoteReturned = testObserver.values().get(0)

        Assert.assertEquals(mNoteMock.description, noteRemoteReturned.description)
        Assert.assertEquals(mNoteMock.sessionId, noteRemoteReturned.sessionId)
        Assert.assertEquals(mNoteMock.topic, noteRemoteReturned.topic)
        Assert.assertEquals(mNoteMock.user, noteRemoteReturned.user)
    }

    @Test
    fun `an invalid note could not be added on server`() {
        mWebServerMock.enqueue(createInvalidSessionResponse())
        val testObserver = noteService.addNote(mNoteMock).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val noteRemoteReturned = testObserver.values().get(0)
        Assert.assertNull(noteRemoteReturned.description)
        Assert.assertNull(noteRemoteReturned.sessionId)
        Assert.assertNull(noteRemoteReturned.topic)
        Assert.assertNull(noteRemoteReturned.user)
    }

    @After
    fun tearDown() {
        mWebServerMock.shutdown()
    }

    private fun createValidSessionResponse(): MockResponse {
        return MockResponse().setBody(mSourceJsonContent)
    }

    private fun createInvalidSessionResponse() : MockResponse {
        return MockResponse().setBody(mSourceInvalidJsonContent)
    }
}