package br.org.cesar.discordtime.stickysessions.injectors.modules

import br.org.cesar.discordtime.stickysessions.data.remote.wrapper.INetworkWrapper
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestNetworkModule {

    @Provides
    @Singleton
    fun provideNetworkWrapper(): INetworkWrapper {
        return mock()
    }
}