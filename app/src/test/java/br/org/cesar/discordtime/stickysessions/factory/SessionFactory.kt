package br.org.cesar.discordtime.stickysessions.factory

import br.org.cesar.discordtime.stickysessions.domain.model.Session
import br.org.cesar.discordtime.stickysessions.factory.DataFactory.Factory.randomString

class SessionFactory {

    companion object Factory {

        fun makeSession(topicsCount: Int): Session {
            val session = Session()
            session.id = randomString()
            session.topics = MutableList(topicsCount) { randomString() }
            session.createdAt = "09.03.2019"
            session.title = "Gain & Pleasure"
            session.description = "Description"
            session.color = "#ffffff"
            return session
        }

        fun makeSession(topicsCount: Int, createdAt: String): Session {
            val session = makeSession(topicsCount)
            session.createdAt = createdAt
            return session
        }

    }
}
