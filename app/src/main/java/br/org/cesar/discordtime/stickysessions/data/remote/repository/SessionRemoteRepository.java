package br.org.cesar.discordtime.stickysessions.data.remote.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote;
import br.org.cesar.discordtime.stickysessions.data.remote.service.SessionService;
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.Mapper;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class SessionRemoteRepository implements SessionRepository {

    private SessionService mService;
    private Mapper<Session, SessionRemote> mMapper;

    public SessionRemoteRepository(SessionService service, Mapper<Session, SessionRemote> mapper) {
        mService = service;
        mMapper = mapper;
    }

    @Override
    public Single<Session> create(List<String> topics) {
        return mService.createSession(topics).map(new Function<SessionRemote, Session>() {
            @Override
            public Session apply(SessionRemote sessionRemote) throws Exception {
                return mMapper.mapToDomain(sessionRemote);
            }
        });
    }

    @Override
    public Single<Session> getSession(String sessionId) {
        return mService.getSession(sessionId).map(new Function<SessionRemote, Session>() {
            @Override
            public Session apply(SessionRemote sessionRemote) throws Exception {
                return mMapper.mapToDomain(sessionRemote);
            }
        });
    }

    @Override
    public Single<List<Session>> listSessions() {
        return mService.getSessions().map(sessionRemotes -> {
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

    private class SessionRemoteComparator implements Comparator<SessionRemote> {
        @Override
        public int compare(SessionRemote sessionRemote, SessionRemote sessionRemoteOther) {
            long secondsRemote = 0;
            long secondsRemoteOther = 0;
            if(sessionRemote != null && sessionRemote.getDate() != null)
                secondsRemote = sessionRemote.getDate().getSeconds();
            if(sessionRemoteOther != null && sessionRemoteOther.getDate() != null)
                secondsRemoteOther = sessionRemoteOther.getDate().getSeconds();

            return (int) (secondsRemoteOther - secondsRemote);
        }
    }
}
