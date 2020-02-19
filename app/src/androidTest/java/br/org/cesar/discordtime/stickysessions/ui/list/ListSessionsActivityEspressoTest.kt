package br.org.cesar.discordtime.stickysessions.ui.list

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.org.cesar.discordtime.stickysessions.MockServerDispatcher
import br.org.cesar.discordtime.stickysessions.R
import br.org.cesar.discordtime.stickysessions.ui.ExtraNames
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ListSessionsActivityEspressoTest {

    @get:Rule
    val activityRule = ActivityTestRule(ListSessionsActivity::class.java, false, false)

    private lateinit var webServer: MockWebServer

    @Before
    fun setUp() {
        webServer = MockWebServer()
        webServer.start(8080)
    }

    @After
    fun tearDown() {
        webServer.shutdown()
    }

    @Test
    fun activityIsUpAndRunning() {

        webServer.setDispatcher(MockServerDispatcher().RequestDispatcher())

        val intent = Intent()
        intent.putExtra(ExtraNames.MEETING_ID, "20.12.2018")

        activityRule.launchActivity(intent)

        onView(withId(R.id.session_list))
                .check(matches(isDisplayed()))
    }

}