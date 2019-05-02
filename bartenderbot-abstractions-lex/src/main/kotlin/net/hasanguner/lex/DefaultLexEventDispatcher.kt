package net.hasanguner.lex

import net.hasanguner.lex.request.LexEventRequest
import net.hasanguner.lex.response.LexEventResponse

class DefaultLexEventDispatcher(
    private val intentHandlers: Collection<LexIntentHandler>
) : LexEventDispatcher {

    override fun dispatch(event: LexEventRequest): LexEventResponse = with(event) {
        intentHandlers.firstOrNull { it supports currentIntent.name }
            ?.handle(this)
            ?: throw InvalidIntentException(currentIntent.name)
    }
}
