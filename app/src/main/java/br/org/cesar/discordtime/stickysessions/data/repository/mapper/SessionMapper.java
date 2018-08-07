package br.org.cesar.discordtime.stickysessions.data.repository.mapper;

import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;

public class SessionMapper implements Mapper<Session, SessionRemote> {
    @Override
    public SessionRemote mapFromDomain(Session domainType) {
        SessionRemote remote = new SessionRemote();
        remote.sessionId = domainType.id;
        remote.topics = domainType.topics;
        return remote;
    }

    @Override
    public Session mapToDomain(SessionRemote dataType) {
        Session session = new Session();
        session.id = dataType.sessionId;
        session.topics = dataType.topics;
        return session;
    }
}
