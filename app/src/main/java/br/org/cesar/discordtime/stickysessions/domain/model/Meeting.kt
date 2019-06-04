package br.org.cesar.discordtime.stickysessions.domain.model

import java.util.*

data class Meeting (
        val id: String,
        val title: String,
        val description: String,
        val location: String,
        val date: Date,
        val sessions: List<Session>,
        val participants: List<String>)