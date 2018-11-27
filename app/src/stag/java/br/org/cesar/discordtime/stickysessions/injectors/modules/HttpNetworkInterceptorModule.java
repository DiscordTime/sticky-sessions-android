package br.org.cesar.discordtime.stickysessions.injectors.modules;

import br.org.cesar.discordtime.stickysessions.data.remote.interceptor.HttpNetworkInterceptor;
import br.org.cesar.discordtime.stickysessions.data.remote.wrapper.INetworkWrapper;
import dagger.Module;
import dagger.Provides;

@Module
public class HttpNetworkInterceptorModule {

    @Provides
    public HttpNetworkInterceptor providesHttpNetworkInterceptor(
            INetworkWrapper networkWrapper) {
        return new HttpNetworkInterceptor(networkWrapper);
    }


}
