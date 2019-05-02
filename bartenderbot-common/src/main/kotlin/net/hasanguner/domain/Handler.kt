package net.hasanguner.domain

interface Handler<T, R> {

    suspend fun execute(request: T): R

}
