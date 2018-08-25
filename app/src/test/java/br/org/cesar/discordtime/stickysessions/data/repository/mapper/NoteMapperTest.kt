package br.org.cesar.discordtime.stickysessions.data.repository.mapper

import br.org.cesar.discordtime.stickysessions.data.remote.model.NoteRemote
import br.org.cesar.discordtime.stickysessions.domain.model.Note
import br.org.cesar.discordtime.stickysessions.factory.NoteFactory
import br.org.cesar.discordtime.stickysessions.factory.NoteRemoteFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NoteMapperTest {

    private lateinit var noteMapper: NoteMapper

    @Before
    fun setUp() {
        noteMapper = NoteMapper()
    }

    @Test
    fun mapFromDomain() {
        val note = NoteFactory.makeNote()
        val noteRemote = noteMapper.mapFromDomain(note)

        assertNoteEquality(note, noteRemote)
    }

    @Test
    fun mapToDomain() {
        val noteRemote = NoteRemoteFactory.makeNoteRemote()
        val note = noteMapper.mapToDomain(noteRemote)

        assertNoteEquality(note, noteRemote)
    }

    private fun assertNoteEquality(note: Note, noteRemote: NoteRemote) {
        assertEquals(note.sessionId, noteRemote.sessionId)
        assertEquals(note.user, noteRemote.user)
        assertEquals(note.topic, noteRemote.topic)
        assertEquals(note.description, noteRemote.description)
    }
}