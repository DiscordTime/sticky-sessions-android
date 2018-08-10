package br.org.cesar.discordtime.stickysessions.domain.repository;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import io.reactivex.Single;

public interface SessionRepository {
    public Single<Session> create(List<String> topics);
    public Single<Session> getSession(String sessionId);
}
