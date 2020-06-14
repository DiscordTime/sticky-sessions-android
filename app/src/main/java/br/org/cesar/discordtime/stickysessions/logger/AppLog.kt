package br.org.cesar.discordtime.stickysessions.logger;

import android.util.Log;

import br.org.cesar.discordtime.stickysessions.BuildConfig;

class AppLog : Logger {
    companion object {
        const val TAG = "StickySessions"
    }

    override fun v(tag: String, message: String) {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "[$tag]: $message")
        }
    }

    override fun d(tag: String, message: String) {
        if (Log.isLoggable(TAG, Log.DEBUG) || BuildConfig.DEBUG) {
            Log.d(TAG, "[$tag]: $message")
        }
    }

    override fun w(tag: String, message: String) {
        Log.w(TAG, "[$tag]: $message")
    }

    override fun w(tag: String, message: String, e: Throwable?) {
        Log.w(TAG, "[$tag]: $message", e)
    }

    override fun e(tag: String, message: String) {
        Log.e(TAG, "[$tag]: $message")
    }

    override fun e(tag: String, message: String, e: Throwable?) {
        Log.e(TAG, "[$tag]: $message", e)
    }

    override fun i(tag: String, message: String) {
        if (Log.isLoggable(TAG, Log.INFO)) {
            Log.i(TAG, "[$tag]: $message")
        }
    }
}
