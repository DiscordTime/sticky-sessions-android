package br.org.cesar.discordtime.stickysessions.domain.interactor;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Single;

public abstract class UseCase<P, R> {
    public abstract Single<R> execute(P params);
}
