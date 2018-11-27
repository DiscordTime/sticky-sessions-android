package br.org.cesar.discordtime.stickysessions.injectors.modules;

import java.util.ArrayList;
import java.util.List;

import br.org.cesar.discordtime.stickysessions.data.remote.interceptor.HttpNetworkInterceptor;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class HttpInterceptorListModule {

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


}
