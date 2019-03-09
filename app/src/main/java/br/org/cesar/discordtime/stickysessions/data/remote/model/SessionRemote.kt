package br.org.cesar.discordtime.stickysessions.data.remote.model

import com.google.gson.annotations.SerializedName

data class SessionRemote(
        @SerializedName("id") var id: String,
        @SerializedName("topics") var topics: List<String>,
        @SerializedName("timestamp") var timestamp: Long
) {
    constructor(id: String, topics: List<String>) : this(id,topics,0)

    constructor(fromFirebase: SessionRemoteFirebase):
            this(fromFirebase.id, fromFirebase.topics, 1000L * (fromFirebase.timestamp?.seconds ?: 0))
}

