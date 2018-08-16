package br.org.cesar.discordtime.stickysessions.data.remote.service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteServiceFactory<T> {

    private static long TIMEOUT = 120;

    public T makeRemoteService(String baseUrl, boolean isDebug, Class<T> serviceClass) {
        OkHttpClient okHttpClient = makeOkHttpClient(
                makeLoggingInterceptor(isDebug)
        );
        return makeRemoteService(baseUrl, okHttpClient, serviceClass);
    }

    private OkHttpClient makeOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    private HttpLoggingInterceptor makeLoggingInterceptor(boolean isDebug) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (isDebug) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return logging;
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
