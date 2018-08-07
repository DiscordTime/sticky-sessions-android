package br.org.cesar.discordtime.stickysessions.factory

import java.util.*

class DataFactory {

    companion object Factory {

        fun randomUuid(): UUID {
            return UUID.randomUUID()
        }

        fun randomString(): String {
            return UUID.randomUUID().toString()
        }

        fun randomInt(): Int {
            return Math.random().toInt()
        }

    }
}