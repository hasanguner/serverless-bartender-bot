package net.hasanguner.domain

interface EntityConverter<E, D> {

    fun convertToDomainObject(entity: E): D

    fun convertToEntity(domainObject: D): E

    fun convertToDomainObject(entities: List<E>): List<D> =
        entities.map { convertToDomainObject(it) }

    fun convertToEntity(domainObjects: List<D>): List<E> =
        domainObjects.map { convertToEntity(it) }

}