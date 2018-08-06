package br.org.cesar.discordtime.stickysessions.executor

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * MainThread (also where UI runs) implementation based on a [Scheduler]
 * which will execute actions on the Android main thread
 */
class MainThread internal constructor() : PostExecutionThread {

    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()

}