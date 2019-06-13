package br.org.cesar.discordtime.stickysessions.executor;

import org.jetbrains.annotations.NotNull;

import br.org.cesar.discordtime.stickysessions.domain.interactor.UseCase;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class TestObservableUseCase<P, R> implements IObservableUseCase<P, R>{

    protected final CompositeDisposable disposables;
    private final UseCase<P, R> useCase;

    public TestObservableUseCase(@NotNull UseCase<P,R> useCase) {
        this.useCase = useCase;
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void execute(@NotNull DisposableSingleObserver observer, P params) {
        Single<R> single = useCase.execute(params)
                .subscribeOn(Schedulers.trampoline());
        addDisposable(single.subscribeWith(observer));
    }

    @Override
    public final void dispose() {
        if (!this.disposables.isDisposed()) {
            this.disposables.dispose();
        }
    }

    private void addDisposable(Disposable disposable) {
        this.disposables.add(disposable);
    }
}
