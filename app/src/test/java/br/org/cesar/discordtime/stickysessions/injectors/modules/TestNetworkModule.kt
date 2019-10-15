package br.org.cesar.discordtime.stickysessions.injectors.modules

import br.org.cesar.discordtime.stickysessions.data.remote.wrapper.INetworkWrapper
import br.org.cesar.discordtime.stickysessions.data.remote.wrapper.NetworkWrapperTest
import dagger.Module
import dagger.Provides

@Module
class TestNetworkModule {

    @Provides
    fun provideNetworkWrapper(): INetworkWrapper {
        return NetworkWrapperTest()
    }
}