package br.org.cesar.discordtime.stickysessions.data.repository.mapper;

import org.jetbrains.annotations.NotNull;

import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;

public class SessionMapper implements Mapper<Session, SessionRemote> {
    @Override
    public SessionRemote mapFromDomain(@NotNull Session domainType) {
        SessionRemote remote = new SessionRemote();
        remote.id = domainType.id;
        remote.topics = domainType.topics;
        return remote;
    }

    @Override
    public Session mapToDomain(@NotNull SessionRemote dataType) {
        Session session = new Session();
        session.id = dataType.id;
        session.topics = dataType.topics;
        return session;
    }
}
