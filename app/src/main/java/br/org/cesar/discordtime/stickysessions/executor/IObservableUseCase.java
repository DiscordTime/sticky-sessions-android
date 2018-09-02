package br.org.cesar.discordtime.stickysessions.executor;

import org.jetbrains.annotations.NotNull;

import io.reactivex.observers.DisposableSingleObserver;

public interface IObservableUseCase<P, R> {
    void execute(@NotNull DisposableSingleObserver observer, P params);
    void dispose();
}
