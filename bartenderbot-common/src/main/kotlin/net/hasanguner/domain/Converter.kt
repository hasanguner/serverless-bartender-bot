package net.hasanguner.domain

interface Converter<in T, out R> {

    fun convert(content: T): R

    fun convert(content: Collection<T>): Collection<R> =
        content.map { convert(it) }

}