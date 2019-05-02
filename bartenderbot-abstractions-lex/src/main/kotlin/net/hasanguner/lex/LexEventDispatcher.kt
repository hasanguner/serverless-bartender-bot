package net.hasanguner.lex

import net.hasanguner.lex.request.LexEventRequest
import net.hasanguner.lex.response.LexEventResponse

interface LexEventDispatcher {

    fun dispatch(event: LexEventRequest): LexEventResponse
}