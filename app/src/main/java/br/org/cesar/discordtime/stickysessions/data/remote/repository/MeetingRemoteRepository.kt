package br.org.cesar.discordtime.stickysessions.data.remote.repository

import br.org.cesar.discordtime.stickysessions.domain.model.Meeting
import br.org.cesar.discordtime.stickysessions.domain.model.Session
import br.org.cesar.discordtime.stickysessions.domain.repository.MeetingRepository
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository
import io.reactivex.Single
import java.util.*
import kotlin.collections.HashMap

class MeetingRemoteRepository(
        val sessionRepository: SessionRepository): MeetingRepository  {

    private val mSessionRepository = sessionRepository

    override fun listMeetings(): Single<List<Meeting>> {
        return mSessionRepository.listSessions().map<List<Meeting>> { sessions ->
            val meetings = ArrayList<Meeting>()

            // group by date
            val sessionsMapByDate = HashMap<String, MutableList<Session>>()
            for (session in sessions) {
                if (session?.createdAt != null && !session.createdAt.isEmpty()) {
                    if (!sessionsMapByDate.containsKey(session.createdAt)){
                        sessionsMapByDate[session.createdAt] = ArrayList()
                    }
                    sessionsMapByDate[session.createdAt]?.add(session)
                }
            }

            // Convert to meetings
            for (groupOfSessions in sessionsMapByDate) {
                // TODO: returning hardcoded values until server gets ready
                meetings.add(Meeting(
                        id = groupOfSessions.key,
                        title = "Retrospective",
                        description = "Bi-weekly meeting to discuss pain points, " +
                                "celebrate achievements and define common actions " +
                                "to all CBS teams.",
                        location = "Luiz Gonzaga_Auditorio-TIR-PT",
                        sessions = groupOfSessions.value,
                        participants = listOf(
                                "Rodrigo Perazzo Rabelo",
                                "Carlos Gabriel da Silva Santana",
                                "Carlos Rodrigo Cordeiro Garcia",
                                "Haldny da Silva dos Santos",
                                "Jose Carlos de Moura Júnior",
                                "Vinicius Araujo de Albuquerque",
                                "José Henrique da Silva Lima",
                                "Ana Caroline Saraiva Bezerra",
                                "Antonio Severino da Silva Junior",
                                "Eduardo Henrique Alves Maia Mattos Oliveira",
                                "João Rafael Santos Camelo",
                                "Patrick Pamponet Steiger",
                                "Thiago Alves Bastos",
                                "Vitor Falcao Poncell",
                                "Victor Hugo Rattis de Araújo",
                                "Wellington Cabral da Silva"),
                        date = groupOfSessions.value[0].run {
                            val c = Calendar.getInstance(Locale.US)
                            c.set(this.year, this.month, this.day)
                            c.set(Calendar.HOUR_OF_DAY, 15)
                            Date(c.timeInMillis)
                        }
                ))
            }

            meetings
        }

    }
}