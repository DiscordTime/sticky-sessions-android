package br.org.cesar.discordtime.stickysessions.injectors.components;

import br.org.cesar.discordtime.stickysessions.injectors.modules.ContextModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.ListSessionsModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.LoggerModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.NavigationModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.ServerModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.SessionModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.ThreadModule;
import br.org.cesar.discordtime.stickysessions.ui.list.ListSessionsActivity;
import dagger.Component;

@Component(modules = {
    ContextModule.class,
    SessionModule.class,
    ServerModule.class,
    ListSessionsModule.class,
    LoggerModule.class,
    NavigationModule.class,
    ThreadModule.class,
    ContextModule.class,
})
public interface ListSessionComponent {
    void inject(ListSessionsActivity activity);
}
