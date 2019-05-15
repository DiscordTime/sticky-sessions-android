package br.org.cesar.discordtime.stickysessions.injectors.modules;

import br.org.cesar.discordtime.stickysessions.auth.TokenProvider;
import br.org.cesar.discordtime.stickysessions.auth.firebase.FirebaseTokenProvider;
import br.org.cesar.discordtime.stickysessions.auth.interceptor.UserTokenInterceptor;
import dagger.Module;
import dagger.Provides;

@Module
public class AuthModule {

    @Provides
    public UserTokenInterceptor providesUserTokenInterceptor(TokenProvider tokenProvider) {
        return new UserTokenInterceptor(tokenProvider);
    }

    @Provides
    public TokenProvider providesTokenProvider() {
        return new FirebaseTokenProvider();
    }
}
