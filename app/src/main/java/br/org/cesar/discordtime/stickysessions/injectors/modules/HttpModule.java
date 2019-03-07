package br.org.cesar.discordtime.stickysessions.injectors.modules;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public List<Interceptor> providesInterceptorList(
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
                                         List<Interceptor> interceptors) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        for (Interceptor interceptor : interceptors) {
            builder.addNetworkInterceptor(interceptor);
        }

        return builder
                .cache(new Cache(context.getCacheDir(), CACHE_SIZE))
                .build();
    }


}
