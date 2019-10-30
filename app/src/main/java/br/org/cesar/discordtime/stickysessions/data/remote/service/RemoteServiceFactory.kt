package br.org.cesar.discordtime.stickysessions.data.remote.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RemoteServiceFactory<T> {

    private val gson: Gson
        get() = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    fun makeRemoteService(baseUrl: String, serviceClass: Class<T>,
                          okHttpClient: OkHttpClient): T {
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        return retrofit.create(serviceClass)
    }

}
