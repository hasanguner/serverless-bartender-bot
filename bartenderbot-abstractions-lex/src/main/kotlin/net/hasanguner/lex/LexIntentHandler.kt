package net.hasanguner.lex

import net.hasanguner.lex.request.LexEventRequest
import net.hasanguner.lex.response.LexEventResponse

interface LexIntentHandler {

    fun handle(event: LexEventRequest): LexEventResponse

    infix fun supports(intentName: String): Boolean
}
