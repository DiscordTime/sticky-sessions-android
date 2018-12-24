package br.org.cesar.discordtime.stickysessions.injectors

import br.org.cesar.discordtime.stickysessions.data.remote.interceptor.HttpNetworkInterceptor
import br.org.cesar.discordtime.stickysessions.injectors.modules.HttpModule
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.util.ArrayList

@Module
class MockHttpModule: HttpModule() {

    @Provides
    override fun providesInterceptorList(
            loggingInterceptor: HttpLoggingInterceptor,
            networkInterceptor: HttpNetworkInterceptor
    ): List<Interceptor> {
        val interceptors = ArrayList<Interceptor>()
        interceptors.add(loggingInterceptor)
        return interceptors
    }
}