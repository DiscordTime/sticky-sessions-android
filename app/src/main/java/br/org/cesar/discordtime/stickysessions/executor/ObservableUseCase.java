package br.org.cesar.discordtime.stickysessions.executor;

import org.jetbrains.annotations.NotNull;

import br.org.cesar.discordtime.stickysessions.domain.interactor.UseCase;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ObservableUseCase<P, R> {

    protected final CompositeDisposable disposables;
    private final UseCase<P, R> useCase;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    public ObservableUseCase(@NotNull UseCase<P,R> useCase,
                             @NotNull ThreadExecutor threadExecutor,
                             @NotNull PostExecutionThread postExecutionThread) {
        this.useCase = useCase;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    public void execute(@NotNull DisposableSingleObserver observer, P params) {
        Single<R> single = useCase.execute(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(single.subscribeWith(observer));
    }

    public final void dispose() {
        if (!this.disposables.isDisposed()) {
            this.disposables.dispose();
        }
    }

    private void addDisposable(Disposable disposable) {
        this.disposables.add(disposable);
    }
}
