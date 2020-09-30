package br.org.cesar.discordtime.stickysessions.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SessionRemoteFirebase(
        @Expose @SerializedName("id") var id: String,
        @Expose @SerializedName("topics") var topics: List<String>,
        @Expose @SerializedName("timestamp") var timestamp: TimeStampRemote?,
        @Expose @SerializedName("title") var title: String?,
        @Expose @SerializedName("description") var description: String?,
        @Expose @SerializedName("color") var color: String?
) {
    constructor(id: String, topics: List<String>, title: String, description: String?,
                color: String?) : this(id,topics,TimeStampRemote(0),title,description,color)
}

data class TimeStampRemote(
        @Expose @SerializedName("_seconds") var seconds: Long
)
