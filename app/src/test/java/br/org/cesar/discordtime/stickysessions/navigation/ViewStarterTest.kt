package br.org.cesar.discordtime.stickysessions.navigation

import android.content.Intent
import br.org.cesar.discordtime.stickysessions.AndroidTest
import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidViewNameException
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.ViewStarter
import br.org.cesar.discordtime.stickysessions.ui.ViewNames
import br.org.cesar.discordtime.stickysessions.ui.lobby.LobbyActivity
import br.org.cesar.discordtime.stickysessions.ui.session.SessionActivity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.robolectric.Robolectric
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf

class ViewStarterTest : AndroidTest() {

    private lateinit var mViewStarter : ViewStarter;

    @Before
    fun setUp(){
        mViewStarter = ViewStarter()
    }

    @Test
    fun `should start activity when viewName is valid`() {
        mViewStarter.goNext(super.context(), ViewNames.SESSION_ACTIVITY, true)
        val expectedIntent = Intent(super.context(), SessionActivity::class.java)
        val actual = shadowOf(RuntimeEnvironment.application).nextStartedActivity
        assertEquals(expectedIntent.component, actual.component)
        assertEquals( actual.flags,
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }

    @Test(expected = InvalidViewNameException::class)
    fun `should throw InvalidViewNameException if viewName is unmapped`(){
        mViewStarter.goNext(super.context(), "unmapped_view_name", true)
    }

}