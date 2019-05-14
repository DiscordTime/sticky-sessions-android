package br.org.cesar.discordtime.stickysessions.injectors.components;

import br.org.cesar.discordtime.stickysessions.injectors.modules.LoggerModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.NavigationModule;
import br.org.cesar.discordtime.stickysessions.ui.login.LoginActivity;
import dagger.Component;

@Component(modules = {
    NavigationModule.class,
    LoggerModule.class
})
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
