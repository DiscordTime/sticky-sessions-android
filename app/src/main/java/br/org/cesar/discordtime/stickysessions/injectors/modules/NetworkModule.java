package br.org.cesar.discordtime.stickysessions.injectors.modules;

import android.content.Context;

import br.org.cesar.discordtime.stickysessions.data.remote.wrapper.INetworkWrapper;
import br.org.cesar.discordtime.stickysessions.data.remote.wrapper.NetworkWrapper;
import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Provides
    public INetworkWrapper provideNetworkWrapper(Context context){
        return new NetworkWrapper(context);
    }

}
