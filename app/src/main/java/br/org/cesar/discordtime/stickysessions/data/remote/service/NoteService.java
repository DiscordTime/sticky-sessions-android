package br.org.cesar.discordtime.stickysessions.data.remote.service;

import br.org.cesar.discordtime.stickysessions.data.remote.model.NoteRemote;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NoteService {
    @POST("/notes")
    Single<NoteRemote> addNote(@Body NoteRemote note);
}
