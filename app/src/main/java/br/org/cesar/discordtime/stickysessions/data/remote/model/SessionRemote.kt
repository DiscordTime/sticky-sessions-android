package br.org.cesar.discordtime.stickysessions.data.remote.model

import com.google.gson.annotations.SerializedName

data class SessionRemote(
        @SerializedName("session_id") var id: String,
        @SerializedName("topics") var topics: List<String>,
        @SerializedName("date") var date: Long
) {
    constructor(id: String, topics: List<String>) : this(id,topics,0)
}
