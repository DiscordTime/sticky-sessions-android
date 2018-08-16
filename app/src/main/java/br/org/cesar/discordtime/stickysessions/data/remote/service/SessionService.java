package br.org.cesar.discordtime.stickysessions.data.remote.service;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SessionService {

    @POST("/sessions")
    Single<SessionRemote> createSession(@Body List<String> topics);

    @GET("/sessions/{id}")
    Single<SessionRemote> getSession(@Path("id") String sessionId);

}
