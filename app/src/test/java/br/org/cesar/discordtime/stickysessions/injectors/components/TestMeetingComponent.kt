package br.org.cesar.discordtime.stickysessions.injectors.components

import br.org.cesar.discordtime.stickysessions.injectors.modules.*
import br.org.cesar.discordtime.stickysessions.presentation.meeting.MeetingPresenterTest
import dagger.Component

@Component(modules = [
    TestMeetingModule::class,
    MeetingPresenterModule::class,
    TestSessionModule::class,
    TestServerModule::class,
    TestNavigationModule::class,
    TestHttpModule::class,
    TestNetworkModule::class
])
interface TestMeetingComponent {
    fun inject(integrationTest: MeetingPresenterTest)
}