package br.org.cesar.discordtime.stickysessions.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class NoteRemote (

    @SerializedName("id")
    @Expose
    @JvmField  var id: String? = null,
    @SerializedName("description")
    @Expose
    @JvmField var description: String? = null,
    @SerializedName("user")
    @Expose(serialize = false)
    @JvmField var user: String? = null,
    @SerializedName("topic")
    @Expose
    @JvmField var topic: String? = null,
    @SerializedName("session_id")
    @Expose
    @JvmField var sessionId: String? = null
)
