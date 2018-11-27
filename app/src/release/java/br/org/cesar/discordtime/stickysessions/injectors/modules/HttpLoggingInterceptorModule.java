package br.org.cesar.discordtime.stickysessions.injectors.modules;

import dagger.Module;
import dagger.Provides;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class HttpLoggingInterceptorModule {

    @Provides
    public HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        return interceptor;
    }


}
