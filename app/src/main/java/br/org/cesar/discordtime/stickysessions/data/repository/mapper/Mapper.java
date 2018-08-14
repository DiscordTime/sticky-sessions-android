package br.org.cesar.discordtime.stickysessions.data.repository.mapper;

import org.jetbrains.annotations.NotNull;

public interface Mapper<A, B> {

    B mapFromDomain(@NotNull A domainType);
    A mapToDomain(@NotNull B dataType);
}
