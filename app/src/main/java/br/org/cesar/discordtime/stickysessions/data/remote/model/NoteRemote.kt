package br.org.cesar.discordtime.stickysessions.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class NoteRemote (

    @SerializedName("id")
    @Expose
    var id: String? = null,
    @SerializedName("description")
    @Expose
    var description: String? = null,
    @SerializedName("user")
    @Expose(serialize = false)
    var user: String? = null,
    @SerializedName("topic")
    @Expose
    var topic: String? = null,
    @SerializedName("session_id")
    @Expose
    var sessionId: String? = null
)
