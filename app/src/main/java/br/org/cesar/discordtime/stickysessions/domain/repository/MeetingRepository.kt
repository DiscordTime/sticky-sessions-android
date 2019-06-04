package br.org.cesar.discordtime.stickysessions.domain.repository

import br.org.cesar.discordtime.stickysessions.domain.model.Meeting
import io.reactivex.Single

interface MeetingRepository {
    fun listMeetings(): Single<List<Meeting>>
}