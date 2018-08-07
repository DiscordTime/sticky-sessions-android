package br.org.cesar.discordtime.stickysessions.data.repository.mapper;

public interface Mapper<A, B> {

    public B mapFromDomain(A domainType);
    public A mapToDomain(B dataType);
}
