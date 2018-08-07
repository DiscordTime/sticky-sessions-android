
package br.org.cesar.discordtime.stickysessions.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SessionRemote {

    @SerializedName("session_id")
    @Expose
    public String sessionId;
    @SerializedName("topics")
    @Expose
    public List<String> topics = null;
    @SerializedName("date")
    @Expose
    public long date;


    public SessionRemote(){}

}
