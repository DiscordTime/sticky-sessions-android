package br.org.cesar.discordtime.stickysessions.injectors.components

import br.org.cesar.discordtime.stickysessions.injectors.modules.MeetingPresenterModule
import br.org.cesar.discordtime.stickysessions.injectors.modules.TestHttpModule
import br.org.cesar.discordtime.stickysessions.injectors.modules.TestMeetingModule
import br.org.cesar.discordtime.stickysessions.injectors.modules.TestNavigationModule
import br.org.cesar.discordtime.stickysessions.injectors.modules.TestServerModule
import br.org.cesar.discordtime.stickysessions.injectors.modules.TestSessionModule
import br.org.cesar.discordtime.stickysessions.presentation.meeting.MeetingPresenterTest
import dagger.Component

@Component(modules = [
    TestMeetingModule::class,
    MeetingPresenterModule::class,
    TestSessionModule::class,
    TestServerModule::class,
    TestNavigationModule::class,
    TestHttpModule::class
])
interface TestMeetingComponent {
    fun inject(integrationTest: MeetingPresenterTest)
}
