package br.org.cesar.discordtime.stickysessions.injectors.modules;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import br.org.cesar.discordtime.stickysessions.auth.interceptor.UserTokenInterceptor;
import br.org.cesar.discordtime.stickysessions.data.remote.interceptor.HttpNetworkInterceptor;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class HttpModule {

    private static final int CACHE_SIZE = 10 * 1024 * 1024;

    @Provides
    @Named("AppInterceptors")
    public List<Interceptor> providesAppInterceptors(
            UserTokenInterceptor userTokenInterceptor
    ){
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(userTokenInterceptor);
        return interceptors;
    }

    @Provides
    @Named("NetworkInterceptors")
    public List<Interceptor> providesNetworkInterceptors(
            HttpLoggingInterceptor loggingInterceptor,
            HttpNetworkInterceptor networkInterceptor
    ) {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(loggingInterceptor);
        interceptors.add(networkInterceptor);
        return interceptors;
    }

    @Provides
    public OkHttpClient makeOkHttpClient(Context context,
                @Named("AppInterceptors") List<Interceptor> appInterceptors,
                @Named("NetworkInterceptors") List<Interceptor> networkInterceptors) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // Add all application interceptors
        for (Interceptor interceptor : appInterceptors) {
            builder.addInterceptor(interceptor);
        }

        // Add all network interceptors
        for (Interceptor interceptor : networkInterceptors) {
            builder.addNetworkInterceptor(interceptor);
        }

        return builder
                .cache(new Cache(context.getCacheDir(), CACHE_SIZE))
                .build();
    }
}
