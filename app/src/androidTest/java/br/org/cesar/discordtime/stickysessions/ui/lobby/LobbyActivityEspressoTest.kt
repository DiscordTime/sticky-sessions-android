package br.org.cesar.discordtime.stickysessions.ui.lobby

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import br.org.cesar.discordtime.stickysessions.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class LobbyActivityEspressoTest {

    @get:Rule
    val activityRule = ActivityTestRule(LobbyActivity::class.java)

    @Test
    fun listGoesLeftAndRight() {
        onView(withId(R.id.recycler_view))
                .perform(swipeLeft())
                .perform(swipeLeft())
                .perform(swipeLeft())
                .perform(swipeRight())
                .perform(swipeRight())
                .perform(swipeRight())
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }
}