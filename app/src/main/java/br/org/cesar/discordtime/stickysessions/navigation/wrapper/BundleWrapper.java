package br.org.cesar.discordtime.stickysessions.navigation.wrapper;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class BundleWrapper implements IBundle {

    private Map<String,String> mExtrasMap;

    public BundleWrapper() {
        mExtrasMap = new HashMap<String, String>();
    }

    @Override
    public void putString(String key, String data) {
        mExtrasMap.put(key, data);
    }

    @Override
    public Map<String,String> getExtras() {
        return mExtrasMap;
    }
}
