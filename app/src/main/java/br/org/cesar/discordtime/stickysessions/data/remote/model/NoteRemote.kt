package br.org.cesar.discordtime.stickysessions.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NoteRemote(
        @Expose @SerializedName("id")
        var id: String? = "",
        @Expose @SerializedName("description")
        var description: String? = "",
        @Expose(serialize = false) @SerializedName("user")
        var user: String? = "",
        @Expose @SerializedName("topic")
        var topic: String? = "",
        @Expose @SerializedName("session_id")
        var sessionId: String? = ""
)