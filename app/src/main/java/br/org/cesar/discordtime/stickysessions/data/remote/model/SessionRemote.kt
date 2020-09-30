package br.org.cesar.discordtime.stickysessions.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SessionRemote(
        @Expose @SerializedName("id") var id: String,
        @Expose @SerializedName("topics") var topics: List<String>,
        @Expose @SerializedName("timestamp") var timestamp: Long,
        @Expose @SerializedName("title") var title: String?,
        @Expose @SerializedName("description") var description: String?,
        @Expose @SerializedName("color") var color: String?
) {
    constructor(id: String, topics: List<String>, title: String, description: String?,
                color: String?) : this(id,topics,0,title,description,color)

    constructor(fromFirebase: SessionRemoteFirebase):
            this(fromFirebase.id, fromFirebase.topics,
                    1000L * (fromFirebase.timestamp?.seconds ?: 0), fromFirebase.title,
                    fromFirebase.description, fromFirebase.color)
}

