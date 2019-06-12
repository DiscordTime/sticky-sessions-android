package br.org.cesar.discordtime.stickysessions.factory

import br.org.cesar.discordtime.stickysessions.factory.DataFactory.Factory.randomString
import br.org.cesar.discordtime.stickysessions.factory.DataFactory.Factory.randomInt
import br.org.cesar.discordtime.stickysessions.presentation.meeting.MeetingItem

class MeetingItemFactory {

    companion object Factory {

        fun makeMeetingItem(): MeetingItem {
            return MeetingItem(
                    id = randomString(),
                    title = randomString(),
                    description = randomString(),
                    location = randomString(),
                    date = randomString(),
                    time = randomString(),
                    numOfSessions = randomInt(),
                    numOfParticipants = randomInt(),
                    recent = false
            )
        }
    }
}