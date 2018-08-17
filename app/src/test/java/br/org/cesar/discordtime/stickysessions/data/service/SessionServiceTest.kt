package br.org.cesar.discordtime.stickysessions.data.service

import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote
import br.org.cesar.discordtime.stickysessions.data.remote.service.RemoteServiceFactory
import br.org.cesar.discordtime.stickysessions.data.remote.service.SessionService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import retrofit2.HttpException

class SessionServiceTest {

    private lateinit var sessionService: SessionService
    private lateinit var mockWebServer: MockWebServer
    private lateinit var sessionId: String
    private lateinit var topics: List<String>
    private val date: Long = 1522415925281

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        sessionId = "d6600558-f101-45be-bf8a-4b5aed40cf9f"
        topics = listOf("Less","More","Start","Stop","Keep")
        sessionService = RemoteServiceFactory<SessionService>()
                .makeRemoteService(
                        mockWebServer.url("").toString(),
                        true,
                        SessionService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `create session returns data`() {
        mockWebServer.enqueue(createValidSessionResponse())

        val testObserver = sessionService.createSession(topics).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValue(createValidSessionRemote())
    }

    @Test
    fun `get session returns data`() {
        mockWebServer.enqueue(createValidSessionResponse())

        val testObserver = sessionService.getSession(sessionId).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValue(createValidSessionRemote())
    }

    @Test
    fun `get session call onError when unauthorized`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(401))

        val testObserver = sessionService.getSession(sessionId).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertError(HttpException::class.java)
    }

    private fun createValidSessionResponse(): MockResponse {
        return MockResponse().setBody(
                javaClass.classLoader.getResource("session_ok_response.json").readText()
        )
    }

    private fun createValidSessionRemote(): SessionRemote {
        return SessionRemote(sessionId, topics, date)
    }
}