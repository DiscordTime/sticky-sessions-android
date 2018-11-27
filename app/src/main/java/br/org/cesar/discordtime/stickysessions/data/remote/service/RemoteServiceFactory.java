package br.org.cesar.discordtime.stickysessions.data.remote.service;

import android.content.Context;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteServiceFactory<T> {

    private static final long TIMEOUT = 120;

    private static final int CACHE_SIZE = 10 * 1024 * 1024;

    public T makeRemoteService(Context context, String baseUrl, Class<T> serviceClass,
                               List<Interceptor> interceptors) {
        OkHttpClient okHttpClient =
                makeOkHttpClient(context, interceptors);
        return makeRemoteService(baseUrl, okHttpClient, serviceClass);
    }

    private OkHttpClient makeOkHttpClient(Context context,
                                          List<Interceptor> interceptors) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        for (Interceptor interceptor : interceptors) {
            builder.addNetworkInterceptor(interceptor);
        }

        return builder
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .cache(new Cache(context.getCacheDir(), CACHE_SIZE))
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    private T makeRemoteService(String baseUrl, OkHttpClient okHttpClient, Class<T> serviceClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(serviceClass);
    }

}
