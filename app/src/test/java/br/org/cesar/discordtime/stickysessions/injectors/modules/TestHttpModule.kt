package br.org.cesar.discordtime.stickysessions.injectors.modules

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class TestHttpModule {

    @Provides
    fun makeOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }
}