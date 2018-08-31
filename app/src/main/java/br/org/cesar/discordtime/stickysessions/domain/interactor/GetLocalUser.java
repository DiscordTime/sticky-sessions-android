package br.org.cesar.discordtime.stickysessions.domain.interactor;

import br.org.cesar.discordtime.stickysessions.domain.repository.UserRepository;
import io.reactivex.Single;
import io.reactivex.SingleObserver;

public class GetLocalUser extends UseCase<Void,String> {

    private final UserRepository mUserRepository;

    public GetLocalUser(UserRepository repository) {
        mUserRepository = repository;
    }

    @Override
    public Single<String> execute(Void params) {
        return mUserRepository.getSavedUser();
    }
}
