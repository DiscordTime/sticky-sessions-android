package br.org.cesar.discordtime.stickysessions.data.repository.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType;

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

        if (dataType.getDate() != null) {
            long seconds = dataType.getDate().getSeconds() * 1000L;
            Date date = new Date(seconds);

            session.createdAt = new SimpleDateFormat("dd/MM", Locale.getDefault()).format(date);
        }

        if (session.topics.size() == 4) {
            session.sessionType = SessionType.GAIN_PLEASURE;
        } else if (session.topics.size() == 5) {
            session.sessionType = SessionType.STARFISH;
        } else {
            session.sessionType = SessionType.CUSTOM;
        }

        return session;
    }
}
