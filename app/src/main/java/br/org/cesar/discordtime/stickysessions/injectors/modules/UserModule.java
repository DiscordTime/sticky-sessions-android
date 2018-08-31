package br.org.cesar.discordtime.stickysessions.injectors.modules;

import android.content.Context;

import javax.inject.Named;

import br.org.cesar.discordtime.stickysessions.data.local.repository.UserLocalRepository;
import br.org.cesar.discordtime.stickysessions.domain.interactor.GetLocalUser;
import br.org.cesar.discordtime.stickysessions.domain.interactor.SaveCurrentUser;
import br.org.cesar.discordtime.stickysessions.domain.repository.UserRepository;
import br.org.cesar.discordtime.stickysessions.executor.ObservableUseCase;
import br.org.cesar.discordtime.stickysessions.executor.PostExecutionThread;
import br.org.cesar.discordtime.stickysessions.executor.ThreadExecutor;
import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

    @Provides
    public ObservableUseCase<String, Boolean> providesObservableSaveCurrentUser(
        SaveCurrentUser saveCurrentUser,
        ThreadExecutor threadExecutor,
        PostExecutionThread postExecutionThread) {
        return new ObservableUseCase<>(saveCurrentUser, threadExecutor, postExecutionThread);
    }

    @Provides
    public ObservableUseCase<Void, String> providesObservableGetSavedUser(
        GetLocalUser getLocalUser, ThreadExecutor threadExecutor,
        PostExecutionThread postExecutionThread) {

        return new ObservableUseCase<>(getLocalUser, threadExecutor, postExecutionThread);
    }

    @Provides
    public SaveCurrentUser providesSaveCurrentUser(UserRepository repository) {
        return new SaveCurrentUser(repository);
    }

    @Provides
    public GetLocalUser providesGetLocalUser(UserRepository repository) {
        return new GetLocalUser(repository);
    }

    @Provides
    public UserRepository providesUserRepository(Context context, @Named("prefs") String prefsName) {
        return new UserLocalRepository(context, prefsName);
    }

    @Provides
    @Named("prefs")
    public String providesPreferenceName() {
        return "user_preference";
    }

}
