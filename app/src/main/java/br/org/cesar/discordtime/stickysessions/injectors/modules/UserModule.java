package br.org.cesar.discordtime.stickysessions.injectors.modules;

import br.org.cesar.discordtime.stickysessions.data.local.repository.UserLocalRepository;
import br.org.cesar.discordtime.stickysessions.domain.interactor.GetLocalUser;
import br.org.cesar.discordtime.stickysessions.domain.repository.UserRepository;
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase;
import br.org.cesar.discordtime.stickysessions.executor.ObservableUseCase;
import br.org.cesar.discordtime.stickysessions.executor.PostExecutionThread;
import br.org.cesar.discordtime.stickysessions.executor.ThreadExecutor;
import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

    @Provides
    public IObservableUseCase<Void, String> providesObservableGetSavedUser(
        GetLocalUser getLocalUser, ThreadExecutor threadExecutor,
        PostExecutionThread postExecutionThread) {

        return new ObservableUseCase<>(getLocalUser, threadExecutor, postExecutionThread);
    }

    @Provides
    public GetLocalUser providesGetLocalUser(UserRepository repository) {
        return new GetLocalUser(repository);
    }

    @Provides
    public UserRepository providesUserRepository() {
        return new UserLocalRepository();
    }

}
