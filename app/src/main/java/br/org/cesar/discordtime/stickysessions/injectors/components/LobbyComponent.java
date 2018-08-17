package br.org.cesar.discordtime.stickysessions.injectors.components;

import br.org.cesar.discordtime.stickysessions.injectors.modules.LobbyPresenterModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.SessionModule;
import br.org.cesar.discordtime.stickysessions.injectors.modules.ThreadModule;
import br.org.cesar.discordtime.stickysessions.ui.lobby.LobbyActivity;
import dagger.Component;

@Component(
        modules = {
                LobbyPresenterModule.class,
                SessionModule.class,
                ThreadModule.class
        }
)
public interface LobbyComponent {
    void inject(LobbyActivity lobbyActivity);

}
