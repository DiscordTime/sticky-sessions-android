package br.org.cesar.discordtime.stickysessions.factory

import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote

class SessionRemoteFactory {

    companion object Factory {

        private fun makeTopicsList(count: Int): List<String> {
            val topics = mutableListOf<String>()
            repeat(count) {
                topics.add(DataFactory.randomString())
            }
            return topics
        }

        fun makeSessionRemote(topicsCount: Int): SessionRemote {
            return SessionRemote(DataFactory.randomString(),
                    makeTopicsList(topicsCount), 1552155381000)
        }

    }
}
