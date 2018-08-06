package br.org.cesar.discordtime.stickysessions.domain.interactor;

import io.reactivex.Single;

public abstract class UseCase<P, R> {
    public abstract Single<R> execute(P params);
}
