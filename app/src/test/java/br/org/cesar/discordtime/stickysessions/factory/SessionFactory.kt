package br.org.cesar.discordtime.stickysessions.factory

import br.org.cesar.discordtime.stickysessions.domain.model.Session
import br.org.cesar.discordtime.stickysessions.factory.DataFactory.Factory.randomString

class SessionFactory {

    companion object Factory {

        private fun makeTopicsList(count: Int): List<String> {
            val topics = mutableListOf<String>()
            repeat(count) {
                topics.add(randomString())
            }
            return topics
        }

        fun makeSession(topicsCount: Int): Session {
            val session = Session()
            session.id = randomString()
            session.topics = makeTopicsList(topicsCount)
            session.createdAt = "09.03.2019"
            return session
        }

        fun makeSession(topicsCount: Int, createdAt: String): Session {
            val session = makeSession(topicsCount)
            session.createdAt = createdAt
            return session
        }

    }
}
