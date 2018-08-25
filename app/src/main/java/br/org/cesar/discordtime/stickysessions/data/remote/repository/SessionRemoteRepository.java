package br.org.cesar.discordtime.stickysessions.data.remote.repository;

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

    public SessionRemoteRepository (SessionService service, Mapper mapper) {
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
}
