package br.org.cesar.discordtime.stickysessions.domain.interactor;


import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType;
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository;
import io.reactivex.Single;

public class CreateSession extends UseCase<SessionType, Session> {

    private static final Map<SessionType, List<String>> mTopicsMap;
    static {
        Map<SessionType, List<String>> map = new EnumMap<>(SessionType.class);
        map.put(SessionType.STARFISH,
                Arrays.asList("Less", "More", "Start", "Stop", "Keep"));
        map.put(SessionType.GAIN_PLEASURE,
                Arrays.asList("Loss & Pain", "Loss & Pleasure", "Gain & Pain", "Gain & Pleasure"));
        mTopicsMap = Collections.unmodifiableMap(map);
    }

    private SessionRepository mRepository;

    public CreateSession(@NotNull SessionRepository repository){
        mRepository = repository;
    }

    @Override
    public Single<Session> execute(SessionType type) {
        if (type != null && mTopicsMap.containsKey(type)) {
            return mRepository.create(mTopicsMap.get(type));
        } else {
            return Single.error(new IllegalArgumentException("Unexpected session type."));
        }
    }
}


