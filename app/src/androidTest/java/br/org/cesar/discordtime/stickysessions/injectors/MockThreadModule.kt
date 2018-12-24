package br.org.cesar.discordtime.stickysessions.injectors

import br.org.cesar.discordtime.stickysessions.executor.TestJobExecutor
import br.org.cesar.discordtime.stickysessions.executor.ThreadExecutor
import br.org.cesar.discordtime.stickysessions.injectors.modules.ThreadModule
import dagger.Module

@Module
class MockThreadModule : ThreadModule() {

    override fun provideThreadExecutor(): ThreadExecutor {
        return TestJobExecutor()
    }

}