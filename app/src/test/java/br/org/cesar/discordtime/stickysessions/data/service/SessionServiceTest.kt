package br.org.cesar.discordtime.stickysessions.data.service

import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote
import br.org.cesar.discordtime.stickysessions.data.remote.service.RemoteServiceFactory
import br.org.cesar.discordtime.stickysessions.data.remote.service.SessionService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.util.*

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
                "{\n" +
                "    \"session_id\": \"d6600558-f101-45be-bf8a-4b5aed40cf9f\",\n" +
                "    \"topics\": [\n" +
                "           \"Less\",\n" +
                "           \"More\",\n" +
                "           \"Start\",\n" +
                "           \"Stop\",\n" +
                "           \"Keep\"\n" +
                "    ],\n" +
                "    \"date\": 1522415925281\n" +
                "}"
        )
    }

    private fun createValidSessionRemote(): SessionRemote {
        return SessionRemote(sessionId, topics, date)
    }
}