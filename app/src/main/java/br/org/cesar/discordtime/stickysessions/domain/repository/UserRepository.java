package br.org.cesar.discordtime.stickysessions.domain.repository;

import io.reactivex.Single;

public interface UserRepository {
    Single<String> getSavedUser();
}
