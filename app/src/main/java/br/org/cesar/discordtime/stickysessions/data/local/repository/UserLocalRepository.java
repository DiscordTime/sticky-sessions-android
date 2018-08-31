package br.org.cesar.discordtime.stickysessions.data.local.repository;

import android.content.Context;
import android.content.SharedPreferences;

import br.org.cesar.discordtime.stickysessions.domain.repository.UserRepository;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class UserLocalRepository implements UserRepository {

    private static final String USER_KEY = "user";

    private final Context mContext;
    private final String mSharedPrefs;

    public UserLocalRepository(Context context, String sharedPrefsName) {
        mContext = context;
        mSharedPrefs = sharedPrefsName;
    }

    @Override
    public Single<Boolean> saveUser(String user) {
        return Single.create(emitter -> {
            SharedPreferences prefs =
                mContext.getSharedPreferences(mSharedPrefs, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(USER_KEY, user);
            
            if (editor.commit()) {
                emitter.onSuccess(true);
            } else {
                emitter.onError(
                    new Exception("The user could not be saved into local repository")
                );
            }
        });
    }

    @Override
    public Single<String> getSavedUser() {
        return Single.create(emitter -> {
            SharedPreferences prefs =
                mContext.getSharedPreferences(mSharedPrefs, Context.MODE_PRIVATE);
            String userSaved = prefs.getString(USER_KEY, null);
            if (userSaved != null) {
                emitter.onSuccess(userSaved);
            } else {
                emitter.onError(new Exception("User Not found"));
            }
            
            
        });
        
    }
}
