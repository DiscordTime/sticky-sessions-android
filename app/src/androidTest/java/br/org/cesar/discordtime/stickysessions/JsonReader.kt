package br.org.cesar.discordtime.stickysessions

import androidx.test.platform.app.InstrumentationRegistry
class JsonReader {

    fun readFromAssets(json: String): String =
        InstrumentationRegistry.getInstrumentation().targetContext.assets.open(json)
                .bufferedReader().use { it.readText() }
}