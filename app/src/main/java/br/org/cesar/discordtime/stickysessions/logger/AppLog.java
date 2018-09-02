package br.org.cesar.discordtime.stickysessions.logger;

import android.util.Log;

public class AppLog implements Logger {

    private static final String TAG = "StickySessions";

    @Override
    public void v(String tag, String message) {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "[" + tag + "]: " + message);
        }
    }

    @Override
    public void d(String tag, String message) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "[" + tag + "]: " + message);
        }
    }

    @Override
    public void w(String tag, String message) {
        Log.w(TAG, "[" + tag + "]: " + message);
    }

    @Override
    public void e(String tag, String message) {
        Log.e(TAG, "[" + tag + "]: " + message);
    }

    @Override
    public void i(String tag, String message) {
        Log.i(TAG, "[" + tag + "]: " + message);
    }
}
