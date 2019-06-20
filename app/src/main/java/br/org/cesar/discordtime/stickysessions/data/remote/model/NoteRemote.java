package br.org.cesar.discordtime.stickysessions.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoteRemote {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("user")
    @Expose(serialize = false)
    public String user;
    @SerializedName("topic")
    @Expose
    public String topic;
    @SerializedName("session_id")
    @Expose
    public String sessionId;

    public NoteRemote(){}
}
