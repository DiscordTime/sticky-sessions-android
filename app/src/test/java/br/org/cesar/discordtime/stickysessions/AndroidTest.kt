package br.org.cesar.discordtime.stickysessions

import android.app.Application
import android.content.Context
import org.junit.runner.RunWith
import java.io.File
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Base class for Robolectric data layer tests.
 * Inherit from this class to create a test.
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class,
        application = AndroidTest.ApplicationStub::class,
        sdk = intArrayOf(24))

abstract class AndroidTest {

    fun context(): Context {
        return RuntimeEnvironment.application
    }

    fun cacheDir(): File {
        return context().cacheDir
    }

    internal class ApplicationStub : Application()
}