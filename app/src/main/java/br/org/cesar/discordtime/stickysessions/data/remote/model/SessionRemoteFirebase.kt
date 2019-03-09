package br.org.cesar.discordtime.stickysessions.data.remote.model

import com.google.gson.annotations.SerializedName

data class SessionRemoteFirebase(
        @SerializedName("id") var id: String,
        @SerializedName("topics") var topics: List<String>,
        @SerializedName("timestamp") var timestamp: TimeStampRemote?
) {
    constructor(id: String, topics: List<String>) : this(id,topics,TimeStampRemote(0))
}

data class TimeStampRemote(
        @SerializedName("_seconds") var seconds: Long
)
