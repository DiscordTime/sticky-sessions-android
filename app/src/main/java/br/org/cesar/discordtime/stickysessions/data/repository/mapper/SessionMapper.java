package br.org.cesar.discordtime.stickysessions.data.repository.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;

public class SessionMapper implements Mapper<Session, SessionRemote> {
    @Override
    public SessionRemote mapFromDomain(Session domainType) {
        if (domainType == null|| domainType.topics == null) {
            return null;
        }
        return new SessionRemote(domainType.id,domainType.topics);
    }

    @Override
    public Session mapToDomain(SessionRemote dataType) {
        if (dataType == null || dataType.getTopics() == null) {
            return null;
        }
        Session session = new Session();
        session.id = dataType.getId();
        session.topics = dataType.getTopics();

        if (dataType.getDate() != null) {
            long seconds = dataType.getDate().getSeconds() * 1000L;
            Date date = new Date(seconds);

            session.createdAt = new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(date);
        }

        return session;
    }
}
