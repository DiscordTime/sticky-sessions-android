package br.org.cesar.discordtime.stickysessions.data.remote.service;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.data.remote.model.NoteRemote;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NoteService {
    @POST("/notes")
    Single<NoteRemote> addNote(@Body NoteRemote note);

    @GET("/notes/{id}")
    Single<List<NoteRemote>> listNotesForSession(@Path("id") String id);
}
