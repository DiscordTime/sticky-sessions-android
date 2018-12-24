package br.org.cesar.discordtime.stickysessions.injectors

import br.org.cesar.discordtime.stickysessions.injectors.modules.ServerModule
import dagger.Module
import dagger.Provides

@Module
class MockServerModule: ServerModule() {

    @Provides
    override fun WebUrlProvider(): String {
        return "http://localhost:8080/"
    }

}