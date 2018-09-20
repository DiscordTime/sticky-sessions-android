package br.org.cesar.discordtime.stickysessions.app;

import android.app.Application;

import br.org.cesar.discordtime.stickysessions.domain.interactor.ListSessions;
import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerListSessionComponent;
import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerLobbyComponent;
import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerSessionComponent;
import br.org.cesar.discordtime.stickysessions.injectors.modules.ContextModule;
import br.org.cesar.discordtime.stickysessions.ui.list.ListSessionsActivity;
import br.org.cesar.discordtime.stickysessions.ui.lobby.LobbyActivity;
import br.org.cesar.discordtime.stickysessions.ui.session.SessionActivity;

public class StickySessionApplication extends Application {

    protected DaggerLobbyComponent.Builder mLobbyComponentBuilder;
    protected DaggerSessionComponent.Builder mSessionComponentBuilder;
    protected DaggerListSessionComponent.Builder mSessionListBuilder;


    @Override
    public void onCreate() {
        super.onCreate();
        configureMainInjectorBuilder();
        configureSessionInjectorBuilder();
        configureSessionListInjectorBuilder();
    }


    protected void configureMainInjectorBuilder() {
        mLobbyComponentBuilder = DaggerLobbyComponent.builder()
            .contextModule(new ContextModule(getApplicationContext()));
    }

    private void configureSessionInjectorBuilder() {
        mSessionComponentBuilder = DaggerSessionComponent.builder()
            .contextModule(new ContextModule(getApplicationContext()));
    }

    private void configureSessionListInjectorBuilder() {
        mSessionListBuilder = DaggerListSessionComponent.builder()
            .contextModule(new ContextModule(getApplicationContext()));
    }

    public void inject(LobbyActivity activity) {
        mLobbyComponentBuilder.build().inject(activity);
    }

    public void inject(SessionActivity activity) {
        mSessionComponentBuilder.build().inject(activity);
    }

    public void inject(ListSessionsActivity activity) {
        mSessionListBuilder.build().inject(activity);
    }

}
