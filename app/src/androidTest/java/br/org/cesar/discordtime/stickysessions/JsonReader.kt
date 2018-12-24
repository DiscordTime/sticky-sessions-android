package br.org.cesar.discordtime.stickysessions

import androidx.test.InstrumentationRegistry

class JsonReader {

    fun readFromAssets(json: String): String =
        InstrumentationRegistry.getContext().assets.open(json)
                .bufferedReader().use { it.readText() }
}