package br.org.cesar.discordtime.stickysessions.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SessionRemote(
        @Expose @SerializedName("id") var id: String,
        @Expose @SerializedName("topics") var topics: List<String>,
        @Expose @SerializedName("timestamp") var timestamp: Long
) {
    constructor(id: String, topics: List<String>) : this(id,topics,0)

    constructor(fromFirebase: SessionRemoteFirebase):
            this(fromFirebase.id, fromFirebase.topics, 1000L * (fromFirebase.timestamp?.seconds ?: 0))
}

