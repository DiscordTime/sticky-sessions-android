package br.org.cesar.discordtime.stickysessions.data.repository.mapper;

import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;

public class SessionMapper implements Mapper<Session, SessionRemote> {
    @Override
    public SessionRemote mapFromDomain(Session domainType) {
        return new SessionRemote(domainType.id,domainType.topics);
    }

    @Override
    public Session mapToDomain(SessionRemote dataType) {
        Session session = new Session();
        session.id = dataType.getId();
        session.topics = dataType.getTopics();
        return session;
    }
}
