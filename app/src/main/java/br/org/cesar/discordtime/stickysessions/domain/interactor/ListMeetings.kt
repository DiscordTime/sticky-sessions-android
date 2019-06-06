package br.org.cesar.discordtime.stickysessions.domain.interactor

import br.org.cesar.discordtime.stickysessions.domain.model.Meeting
import br.org.cesar.discordtime.stickysessions.domain.repository.MeetingRepository
import io.reactivex.Single

class ListMeetings(repository: MeetingRepository): UseCase<Comparator<Meeting>, MutableList<Meeting>>() {

    private val mRepository = repository

    override fun execute(comparator: Comparator<Meeting>?): Single<MutableList<Meeting>> {
        return mRepository.listMeetings().map {
            comparator?.run { it.sortWith(this) }
            it
        }
    }

}