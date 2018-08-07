package br.org.cesar.discordtime.stickysessions.factory

import br.org.cesar.discordtime.stickysessions.data.remote.model.NoteRemote
import br.org.cesar.discordtime.stickysessions.domain.model.Note

class NoteRemoteFactory {

    companion object Factory {

        fun makeNoteRemote(): NoteRemote {
            var note = NoteRemote()
            note.sessionId = DataFactory.randomString()
            note.user = DataFactory.randomString()
            note.topic = DataFactory.randomString()
            note.description = DataFactory.randomString()
            return note
        }

    }
}