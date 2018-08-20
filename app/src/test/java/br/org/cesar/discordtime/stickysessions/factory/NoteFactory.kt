package br.org.cesar.discordtime.stickysessions.factory

import br.org.cesar.discordtime.stickysessions.domain.model.Note
import br.org.cesar.discordtime.stickysessions.factory.DataFactory.Factory.randomString

class NoteFactory {

    companion object Factory {

        fun makeNote(): Note {
            return Note(randomString(), randomString(), randomString(), randomString())
        }

    }
}