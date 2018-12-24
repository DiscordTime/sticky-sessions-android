package br.org.cesar.discordtime.stickysessions.executor

import androidx.test.espresso.idling.concurrent.IdlingThreadPoolExecutor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit

class TestJobExecutor : JobExecutor() {

    init {
        super.threadPoolExecutor = IdlingThreadPoolExecutor("android_", INITIAL_POOL_SIZE, MAX_POOL_SIZE,
        KEEP_ALIVE_TIME.toLong(), KEEP_ALIVE_TIME_UNIT, super.workQueue, super.threadFactory)
    }

    companion object {

        private const val INITIAL_POOL_SIZE = 3
        private const val MAX_POOL_SIZE = 5

        // Sets the amount of time an idle thread waits before terminating
        private const val KEEP_ALIVE_TIME = 10

        // Sets the Time Unit to seconds
        private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
    }
}