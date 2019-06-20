package br.org.cesar.discordtime.stickysessions.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SessionRemoteFirebase(
        @Expose @SerializedName("id") var id: String,
        @Expose @SerializedName("topics") var topics: List<String>,
        @Expose @SerializedName("timestamp") var timestamp: TimeStampRemote?
) {
    constructor(id: String, topics: List<String>) : this(id,topics,TimeStampRemote(0))
}

data class TimeStampRemote(
        @Expose @SerializedName("_seconds") var seconds: Long
)
