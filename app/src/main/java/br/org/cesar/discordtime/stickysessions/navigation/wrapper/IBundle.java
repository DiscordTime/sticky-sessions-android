package br.org.cesar.discordtime.stickysessions.navigation.wrapper;

import java.util.Map;

public interface IBundle {
    void putString(String key, String data);
    Map<String,String> getExtras();
}
