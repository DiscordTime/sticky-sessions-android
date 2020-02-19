package br.org.cesar.discordtime.stickysessions.app

import androidx.test.platform.app.InstrumentationRegistry
import br.org.cesar.discordtime.stickysessions.injectors.MockHttpModule
import br.org.cesar.discordtime.stickysessions.injectors.MockServerModule
import br.org.cesar.discordtime.stickysessions.injectors.MockThreadModule
import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerListSessionComponent
import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerLobbyComponent
import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerSessionComponent
import br.org.cesar.discordtime.stickysessions.injectors.modules.ContextModule

class TestApplication : StickySessionApplication() {

    override fun configureMainInjectorBuilder() {
        mLobbyComponentBuilder = DaggerLobbyComponent.builder()
                .contextModule(ContextModule(InstrumentationRegistry
                        .getInstrumentation().targetContext))
                .threadModule(MockThreadModule())
                .serverModule(MockServerModule())
                .httpModule(MockHttpModule())
    }

    override fun configureSessionInjectorBuilder() {
        mSessionComponentBuilder = DaggerSessionComponent.builder()
                .contextModule(ContextModule(InstrumentationRegistry
                        .getInstrumentation().targetContext))
                .threadModule(MockThreadModule())
                .serverModule(MockServerModule())
                .httpModule(MockHttpModule())
    }

    override fun configureSessionListInjectorBuilder() {
        mSessionListBuilder = DaggerListSessionComponent.builder()
                .contextModule(ContextModule(InstrumentationRegistry
                        .getInstrumentation().targetContext))
                .threadModule(MockThreadModule())
                .serverModule(MockServerModule())
                .httpModule(MockHttpModule())
    }
}
