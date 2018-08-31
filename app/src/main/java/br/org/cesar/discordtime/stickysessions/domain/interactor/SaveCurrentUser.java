package br.org.cesar.discordtime.stickysessions.domain.interactor;

import br.org.cesar.discordtime.stickysessions.domain.repository.UserRepository;
import io.reactivex.Single;

public class SaveCurrentUser extends UseCase<String, Boolean> {

    private UserRepository mUserRepository;

    public SaveCurrentUser(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    public Single<Boolean> execute(String userName) {

        if (userName == null || userName.trim().isEmpty()) {
            return Single.error(new Exception("The user is invalid (null or empty)"));
        }
        
        return mUserRepository.saveUser(userName);
    }
}
