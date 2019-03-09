package br.org.cesar.discordtime.stickysessions.domain.interactor

import br.org.cesar.discordtime.stickysessions.domain.model.Session
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository
import io.reactivex.Single

class RescheduleSession(repository: SessionRepository): UseCase<Session, Session>() {

    private val mRepository = repository

    override fun execute(session: Session): Single<Session> {
        return mRepository.rescheduleSession(session)
    }
}