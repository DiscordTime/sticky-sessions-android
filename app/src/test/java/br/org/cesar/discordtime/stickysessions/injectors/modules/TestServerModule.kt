package br.org.cesar.discordtime.stickysessions.injectors.modules

import dagger.Module
import dagger.Provides

@Module
class TestServerModule {

    @Provides
    fun WebUrlProvider(): String {
        return "http://localhost:8080/"
    }

}
