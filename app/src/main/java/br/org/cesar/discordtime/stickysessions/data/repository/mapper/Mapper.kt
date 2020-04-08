package br.org.cesar.discordtime.stickysessions.data.repository.mapper

interface Mapper<A, B> {

    fun mapFromDomain(domainType: A): B
    fun mapToDomain(dataType: B): A
}
