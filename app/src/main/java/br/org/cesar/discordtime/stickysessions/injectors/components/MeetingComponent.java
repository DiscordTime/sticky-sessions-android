package br.org.cesar.discordtime.stickysessions.injectors.components;

import br.org.cesar.discordtime.stickysessions.injectors.modules.AuthModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.ContextModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.HttpLoggingInterceptorModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.HttpModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.HttpNetworkInterceptorModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.LoggerModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.MeetingModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.MeetingPresenterModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.NavigationModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.NetworkModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.ServerModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.SessionModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.ThreadModule;
import br.org.cesar.discordtime.stickysessions.ui.meeting.MeetingActivity;
import dagger.Component;

@Component(modules = {
        ContextModule.class,
        MeetingModule.class,
        MeetingPresenterModule.class,
        SessionModule.class,
        ServerModule.class,
        LoggerModule.class,
        NavigationModule.class,
        ThreadModule.class,
        NetworkModule.class,
        HttpLoggingInterceptorModule.class,
        HttpNetworkInterceptorModule.class,
        HttpModule.class,
        AuthModule.class
})
public interface MeetingComponent {
    void inject(MeetingActivity activity);
}
