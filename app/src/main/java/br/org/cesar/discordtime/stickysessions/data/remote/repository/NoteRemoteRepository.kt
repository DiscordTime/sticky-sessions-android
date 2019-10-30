package br.org.cesar.discordtime.stickysessions.data.remote.repository

import java.util.ArrayList

import br.org.cesar.discordtime.stickysessions.data.remote.model.NoteRemote
import br.org.cesar.discordtime.stickysessions.data.remote.service.NoteService
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.Mapper
import br.org.cesar.discordtime.stickysessions.domain.model.Note
import br.org.cesar.discordtime.stickysessions.domain.model.NoteFilter
import br.org.cesar.discordtime.stickysessions.domain.repository.NoteRepository
import io.reactivex.Single

class NoteRemoteRepository(private val mNoteService: NoteService, private val mMapper: Mapper<Note, NoteRemote>) : NoteRepository {

    override fun addNote(note: Note): Single<Note> {
        val noteRemoteToAdd = mMapper.mapFromDomain(note)

        return mNoteService.addNote(noteRemoteToAdd).map { noteRemoteReturned -> mMapper.mapToDomain(noteRemoteReturned) }
    }

    override fun listNotesForSession(noteFilter: NoteFilter): Single<List<Note>> {
        return mNoteService.listNotesForSession(noteFilter.idSession).map { noteRemotes ->
            val mNotes = ArrayList<Note>()

            for (noteRemote in noteRemotes) {
                mNotes.add(mMapper.mapToDomain(noteRemote))
            }

            mNotes
        }
    }

    override fun removeNote(note: Note): Single<Boolean> {
        return mNoteService.removeNote(note.id).map { responseBody -> true }
    }
}
