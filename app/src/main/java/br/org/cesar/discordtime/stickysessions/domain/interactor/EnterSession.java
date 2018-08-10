package br.org.cesar.discordtime.stickysessions.domain.interactor;


import org.jetbrains.annotations.NotNull;

import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository;
import io.reactivex.Single;

public class EnterSession extends UseCase<String, Session> {

    private SessionRepository mRepository;

    public EnterSession(@NotNull SessionRepository repository) {
        mRepository = repository;
    }

    @Override
    public Single<Session> execute(@NotNull String sessionId) {
        if (!sessionId.trim().equals("")) {
            return mRepository.getSession(sessionId);
        }
        return Single.error(new IllegalArgumentException("Invalid session Id."));
    }
}
