package br.org.cesar.discordtime.stickysessions.app

import android.app.Application

import com.crashlytics.android.Crashlytics

import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerListSessionComponent
import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerLobbyComponent
import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerLoginComponent
import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerMeetingComponent
import br.org.cesar.discordtime.stickysessions.injectors.components.DaggerSessionComponent
import br.org.cesar.discordtime.stickysessions.injectors.modules.ContextModule
import br.org.cesar.discordtime.stickysessions.ui.list.ListSessionsActivity
import br.org.cesar.discordtime.stickysessions.ui.lobby.LobbyActivity
import br.org.cesar.discordtime.stickysessions.ui.login.LoginActivity
import br.org.cesar.discordtime.stickysessions.ui.meeting.MeetingActivity
import br.org.cesar.discordtime.stickysessions.ui.session.SessionActivity
import io.fabric.sdk.android.Fabric

open class StickySessionApplication : Application() {

    protected var mLoginComponentBuilder: DaggerLoginComponent.Builder
    protected var mLobbyComponentBuilder: DaggerLobbyComponent.Builder
    protected var mSessionComponentBuilder: DaggerSessionComponent.Builder
    protected var mSessionListBuilder: DaggerListSessionComponent.Builder
    protected var mMeetingComponentBuilder: DaggerMeetingComponent.Builder

    override fun onCreate() {
        super.onCreate()
        configureLoginInjectorBuilder()
        configureMainInjectorBuilder()
        configureSessionInjectorBuilder()
        configureSessionListInjectorBuilder()
        configureMeetingInjectorBuilder()

        Fabric.with(this, Crashlytics())
    }

    protected fun configureLoginInjectorBuilder() {
        mLoginComponentBuilder = DaggerLoginComponent.builder()
    }

    protected open fun configureMainInjectorBuilder() {
        mLobbyComponentBuilder = DaggerLobbyComponent.builder()
                .contextModule(ContextModule(applicationContext))
    }

    protected open fun configureSessionInjectorBuilder() {
        mSessionComponentBuilder = DaggerSessionComponent.builder()
                .contextModule(ContextModule(applicationContext))
    }

    protected open fun configureSessionListInjectorBuilder() {
        mSessionListBuilder = DaggerListSessionComponent.builder()
                .contextModule(ContextModule(applicationContext))
    }

    protected fun configureMeetingInjectorBuilder() {
        mMeetingComponentBuilder = DaggerMeetingComponent.builder()
                .contextModule(ContextModule(applicationContext))
    }

    fun inject(activity: LoginActivity) {
        mLoginComponentBuilder.build().inject(activity)
    }

    fun inject(activity: LobbyActivity) {
        mLobbyComponentBuilder.build().inject(activity)
    }

    fun inject(activity: SessionActivity) {
        mSessionComponentBuilder.build().inject(activity)
    }

    fun inject(activity: ListSessionsActivity) {
        mSessionListBuilder.build().inject(activity)
    }

    fun inject(activity: MeetingActivity) {
        mMeetingComponentBuilder.build().inject(activity)
    }

}
