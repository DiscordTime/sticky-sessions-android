
package br.org.cesar.discordtime.stickysessions.data.remote.model;

import com.google.firebase.database.ServerValue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class SessionRemote {

    @Exclude
    public String id;
    @SerializedName("topics")
    @Expose
    public List<String> topics = null;
    @SerializedName("date")
    @Expose
    public long date;

    public SessionRemote() {}

    public SessionRemote(String id, List<String> topics) {
        this.id = id;
        this.topics = topics;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("topics", topics);
        result.put("date", ServerValue.TIMESTAMP);
        return result;
    }

}
