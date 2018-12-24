package br.org.cesar.discordtime.stickysessions.data.service

import android.content.Context
import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote
import br.org.cesar.discordtime.stickysessions.data.remote.model.TimeStampRemote
import br.org.cesar.discordtime.stickysessions.data.remote.service.RemoteServiceFactory
import br.org.cesar.discordtime.stickysessions.data.remote.service.SessionService
import br.org.cesar.discordtime.stickysessions.injectors.modules.HttpModule
import com.nhaarman.mockito_kotlin.mock
import okhttp3.Interceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class SessionServiceTest {

    private lateinit var sessionService: SessionService
    private lateinit var mockWebServer: MockWebServer
    private lateinit var contextMock: Context
    private lateinit var sessionId: String
    private lateinit var starfishTopics: List<String>
    private lateinit var gainPleasureTopics: List<String>
    private val date: Long = 1522415925281
    private lateinit var timeStampRemote : TimeStampRemote

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        timeStampRemote = TimeStampRemote(date)
        sessionId = "d6600558-f101-45be-bf8a-4b5aed40cf9f"
        starfishTopics = listOf("Less","More","Start","Stop","Keep")
        gainPleasureTopics = listOf(
                "Loss & Pleasure (1)",
                "Gain & Pleasure (2)",
                "Loss & Pain (3)",
                "Gain & Pain (4)"
        )
        contextMock = mock()
        val okHttpClient = HttpModule().makeOkHttpClient(contextMock, listOf<Interceptor>())
        sessionService = RemoteServiceFactory<SessionService>()
                .makeRemoteService(
                        mockWebServer.url("").toString(),
                        SessionService::class.java,
                        okHttpClient
                        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `create session returns data`() {
        mockWebServer.enqueue(createValidSessionResponse())
        val testObserver = sessionService.createSession(starfishTopics).test()
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
    fun `get sessions returns list of sessions`() {
        mockWebServer.enqueue(createValidGetSessionsResponse())
        val testObserver = sessionService.sessions.test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValue(createValidSessionsRemoteList())
    }

    @Test
    fun `get session call onError when unauthorized`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(401))
        val testObserver = sessionService.getSession(sessionId).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertError(HttpException::class.java)
    }

    private fun createValidGetSessionsResponse(): MockResponse {
        return MockResponse().setBody(
                javaClass.classLoader.getResource("get_sessions_ok_response.json").readText()
        )
    }

    private fun createValidSessionsRemoteList(): List<SessionRemote> {
        return listOf(
                SessionRemote("2BufnJnKe6K7YO0PEKPS", starfishTopics,
                        TimeStampRemote(1542796501)),
                SessionRemote("zUjA7sqXAbhXJkXDCrRZ", gainPleasureTopics,
                        TimeStampRemote(1545335142))
        )
    }

    private fun createValidSessionResponse(): MockResponse {
        return MockResponse().setBody(
                javaClass.classLoader.getResource("session_ok_response.json").readText()
        )
    }

    private fun createValidSessionRemote(): SessionRemote {
        return SessionRemote(sessionId, starfishTopics, timeStampRemote)
    }

}