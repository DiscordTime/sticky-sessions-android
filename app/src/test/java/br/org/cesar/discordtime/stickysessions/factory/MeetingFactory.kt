package br.org.cesar.discordtime.stickysessions.factory

import br.org.cesar.discordtime.stickysessions.domain.model.Meeting
import br.org.cesar.discordtime.stickysessions.factory.DataFactory.Factory.randomString
import java.util.*

class MeetingFactory {

    companion object Factory {

        fun makeMeeting(sessionsCount: Int, participantsCount: Int, date: Date): Meeting {
            return Meeting(
                    id = randomString(),
                    title = randomString(),
                    description = randomString(),
                    location = randomString(),
                    date = date,
                    sessions = List(sessionsCount) { SessionFactory.makeSession(3) },
                    participants = List(participantsCount) { randomString() }
            )
        }

        fun makeMeeting(sessionsCount: Int, participantsCount: Int, daysFromBase: Int): Meeting {
            val cal = Calendar.getInstance()
            cal.set(Calendar.DAY_OF_YEAR, 100)
            cal.add(Calendar.DAY_OF_YEAR, daysFromBase)
            return makeMeeting(sessionsCount,participantsCount,cal.time)
        }
    }
}