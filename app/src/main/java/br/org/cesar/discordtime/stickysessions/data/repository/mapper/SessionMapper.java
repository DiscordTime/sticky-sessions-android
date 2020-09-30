package br.org.cesar.discordtime.stickysessions.data.repository.mapper;

import android.graphics.Color;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

        long timestamp = 0;
        if (domainType.createdAt != null && !domainType.createdAt.isEmpty()) {
            Calendar c = Calendar.getInstance(Locale.US);
            c.set(domainType.getYear(), domainType.getMonth(), domainType.getDay());
            timestamp = c.getTimeInMillis();
        }
        return new SessionRemote(domainType.id,domainType.topics, timestamp, domainType.title,
                domainType.description, domainType.color);
    }

    @Override
    public Session mapToDomain(SessionRemote dataType) {
        if (dataType == null || dataType.getTopics() == null) {
            return null;
        }
        Session session = new Session();
        session.id = dataType.getId();
        session.topics = dataType.getTopics();
        session.title = "";
        session.description = "";
        session.color = "";

        if (dataType.getTimestamp() != 0) {
            Date date = new Date(dataType.getTimestamp());
            session.createdAt = new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(date);
        }

        if (dataType.getTitle() != null) {
            session.title = dataType.getTitle();
        }

        if (dataType.getDescription() != null) {
            session.description = dataType.getDescription();
        }

        if (dataType.getColor() != null) {
            session.color = dataType.getColor();
        }

        return session;
    }
}
