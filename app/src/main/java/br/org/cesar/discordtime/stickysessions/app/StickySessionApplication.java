package br.org.cesar.discordtime.stickysessions.app;

import android.app.Application;

import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerListSessionComponent;
import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerLobbyComponent;
import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerLoginComponent;
import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerMeetingComponent;
import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerSessionComponent;
import br.org.cesar.discordtime.stickysessions.injectors.modules.ContextModule;
import br.org.cesar.discordtime.stickysessions.ui.list.ListSessionsActivity;
import br.org.cesar.discordtime.stickysessions.ui.lobby.LobbyActivity;
import br.org.cesar.discordtime.stickysessions.ui.login.LoginActivity;
import br.org.cesar.discordtime.stickysessions.ui.meeting.MeetingActivity;
import br.org.cesar.discordtime.stickysessions.ui.session.SessionActivity;

public class StickySessionApplication extends Application {

    protected DaggerLoginComponent.Builder mLoginComponentBuilder;
    protected DaggerLobbyComponent.Builder mLobbyComponentBuilder;
    protected DaggerSessionComponent.Builder mSessionComponentBuilder;
    protected DaggerListSessionComponent.Builder mSessionListBuilder;
    protected DaggerMeetingComponent.Builder mMeetingComponentBuilder;

    @Override
    public void onCreate() {
        super.onCreate();
        configureLoginInjectorBuilder();
        configureMainInjectorBuilder();
        configureSessionInjectorBuilder();
        configureSessionListInjectorBuilder();
        configureMeetingInjectorBuilder();
    }

    protected void configureLoginInjectorBuilder() {
        mLoginComponentBuilder = DaggerLoginComponent.builder();
    }

    protected void configureMainInjectorBuilder() {
        mLobbyComponentBuilder = DaggerLobbyComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()));
    }

    protected void configureSessionInjectorBuilder() {
        mSessionComponentBuilder = DaggerSessionComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()));
    }

    protected void configureSessionListInjectorBuilder() {
        mSessionListBuilder = DaggerListSessionComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()));
    }

    protected void configureMeetingInjectorBuilder() {
        mMeetingComponentBuilder = DaggerMeetingComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()));
    }

    public void inject(LoginActivity activity) {
        mLoginComponentBuilder.build().inject(activity);
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

    public void inject(MeetingActivity activity) {
        mMeetingComponentBuilder.build().inject(activity);
    }

}
