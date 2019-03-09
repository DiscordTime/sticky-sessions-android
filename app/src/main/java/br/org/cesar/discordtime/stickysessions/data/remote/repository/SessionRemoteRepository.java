package br.org.cesar.discordtime.stickysessions.data.remote.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote;
import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemoteFirebase;
import br.org.cesar.discordtime.stickysessions.data.remote.service.SessionService;
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.Mapper;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository;
import io.reactivex.Single;

public class SessionRemoteRepository implements SessionRepository {

    private SessionService mService;
    private Mapper<Session, SessionRemote> mMapper;

    public SessionRemoteRepository(SessionService service, Mapper<Session, SessionRemote> mapper) {
        mService = service;
        mMapper = mapper;
    }

    @Override
    public Single<Session> create(List<String> topics) {
        return mService.createSession(topics)
                .map(SessionRemote::new)
                .map(sessionRemote -> mMapper.mapToDomain(sessionRemote));
    }

    @Override
    public Single<Session> getSession(String sessionId) {
        return mService.getSession(sessionId)
                .map(SessionRemote::new)
                .map(sessionRemote -> mMapper.mapToDomain(sessionRemote));
    }

    @Override
    public Single<List<Session>> listSessions() {
        return mService.getSessions()
                .map(sessionsRemoteFirebase -> {
                    List<SessionRemote> sessionsRemote = new ArrayList<>();
                    for (SessionRemoteFirebase sessionRemoteFirebase: sessionsRemoteFirebase) {
                        sessionsRemote.add(new SessionRemote(sessionRemoteFirebase));
                    }
                    return sessionsRemote;
                })
                .map(sessionRemotes -> {
                List<Session> sessions = new ArrayList<>();

                // TODO: Issue #143 - Move sorting to the presentation layer
                Collections.sort(sessionRemotes, new SessionRemoteComparator());
                for (SessionRemote sessionRemote : sessionRemotes) {
                    Session session = mMapper.mapToDomain(sessionRemote);
                    if(session != null) {
                        sessions.add(session);
                    }
                }

                return sessions;
        });
    }

    @Override
    public Single<Session> rescheduleSession(Session session) {
        return mService.rescheduleSession(session.id, mMapper.mapFromDomain(session))
                .map(SessionRemote::new)
                .map(sessionRemote -> mMapper.mapToDomain(sessionRemote));
    }

    private class SessionRemoteComparator implements Comparator<SessionRemote> {
        @Override
        public int compare(SessionRemote current, SessionRemote other) {
            return (int) ((other.getTimestamp() - current.getTimestamp()) / 1000L);
        }
    }
}
