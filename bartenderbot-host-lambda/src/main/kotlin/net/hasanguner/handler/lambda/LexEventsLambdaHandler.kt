package net.hasanguner.handler.lambda

import net.hasanguner.di.DaggerLexEventsComponent
import net.hasanguner.handler.CoAbstractRequestHandler
import net.hasanguner.handler.LexEventsHandler
import net.hasanguner.lex.request.LexEventRequest
import net.hasanguner.lex.response.LexEventResponse

class LexEventsLambdaHandler : CoAbstractRequestHandler<LexEventRequest, LexEventResponse>() {

    private val lexEventsHandler: LexEventsHandler =
        DaggerLexEventsComponent.create().lexEventsHandler

    override suspend fun handle(request: LexEventRequest): LexEventResponse =
        lexEventsHandler.execute(request)

}
