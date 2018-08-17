package br.org.cesar.discordtime.stickysessions.app;

import android.app.Application;

import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerLobbyComponent;
import br.org.cesar.discordtime.stickysessions.ui.lobby.LobbyActivity;

public class StickySessionApplication extends Application {

    protected DaggerLobbyComponent.Builder mLobbyComponentBuilder;


    @Override
    public void onCreate() {
        super.onCreate();
        configureMainInjectorBuilder();
    }


    protected void configureMainInjectorBuilder() {
        mLobbyComponentBuilder = DaggerLobbyComponent.builder();
    }

    public void inject(LobbyActivity activity) {
        mLobbyComponentBuilder.build().inject(activity);
    }

}
