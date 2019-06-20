package br.org.cesar.discordtime.stickysessions.data.remote.service;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.data.remote.model.NoteRemote;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NoteService {
    @POST("/notes")
    Single<NoteRemote> addNote(@Body NoteRemote note);

    @GET("/notes")
    Single<List<NoteRemote>> listNotesForSession(@Query("session_id") String sessionId);

    @DELETE("/notes/{id}")
    Single<ResponseBody> removeNote(@Path("id") String id);
}
