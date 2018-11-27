package br.org.cesar.discordtime.stickysessions.injectors.modules;

import br.org.cesar.discordtime.stickysessions.data.remote.interceptor.HttpNetworkInterceptor;
import br.org.cesar.discordtime.stickysessions.data.remote.wrapper.INetworkWrapper;
import dagger.Module;
import dagger.Provides;

@Module
public class HttpNetworkInterceptorModule {

    @Provides
    public HttpNetworkInterceptor providesHttpNetworkInterceptorModule(
            INetworkWrapper networkWrapper
    ) {
        HttpNetworkInterceptor httpNetworkInterceptor =
                new HttpNetworkInterceptor(networkWrapper);
        httpNetworkInterceptor.setLocal(true);
        return httpNetworkInterceptor;
    }

}
