package br.org.cesar.discordtime.stickysessions.injectors.modules;

import br.org.cesar.discordtime.stickysessions.BuildConfig;
import dagger.Module;
import dagger.Provides;

@Module
public class ServerModule {

    @Provides
    public String WebUrlProvider(){
        return BuildConfig.URL;
    }

}
