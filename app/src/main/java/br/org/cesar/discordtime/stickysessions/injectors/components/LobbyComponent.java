package br.org.cesar.discordtime.stickysessions.injectors.components;

import br.org.cesar.discordtime.stickysessions.injectors.modules.AuthModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.ContextModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.HttpLoggingInterceptorModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.HttpModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.HttpNetworkInterceptorModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.LobbyPresenterModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.LoggerModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.NavigationModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.NetworkModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.ServerModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.SessionModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.ThreadModule;
import br.org.cesar.discordtime.stickysessions.ui.lobby.LobbyActivity;
import dagger.Component;

@Component(
        modules = {
                LobbyPresenterModule.class,
                SessionModule.class,
                ThreadModule.class,
                ContextModule.class,
                NavigationModule.class,
                ServerModule.class,
                LoggerModule.class,
                NetworkModule.class,
                HttpLoggingInterceptorModule.class,
                HttpNetworkInterceptorModule.class,
                HttpModule.class,
                AuthModule.class
        }
)
public interface LobbyComponent {
    void inject(LobbyActivity lobbyActivity);

}
