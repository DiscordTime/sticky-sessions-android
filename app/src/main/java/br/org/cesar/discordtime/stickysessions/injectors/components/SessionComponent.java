package br.org.cesar.discordtime.stickysessions.injectors.components;

import br.org.cesar.discordtime.stickysessions.injectors.modules.ContextModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.NoteModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.ServerModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.SessionModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.SessionPresenterModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.ThreadModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.UserModule;
import br.org.cesar.discordtime.stickysessions.ui.session.SessionActivity;
import dagger.Component;

@Component(
    modules = {
        SessionPresenterModule.class,
        NoteModule.class,
        SessionModule.class,
        ThreadModule.class,
        ContextModule.class,
        ServerModule.class,
        UserModule.class
    }
)
public interface SessionComponent {
    void inject(SessionActivity activity);
}
