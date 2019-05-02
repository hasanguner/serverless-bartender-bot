package net.hasanguner.handler

import net.hasanguner.domain.Handler
import net.hasanguner.lex.DefaultLexEventDispatcher
import net.hasanguner.lex.LexEventDispatcher
import net.hasanguner.lex.LexIntentHandler
import net.hasanguner.lex.request.LexEventRequest
import net.hasanguner.lex.response.CloseDialogAction
import net.hasanguner.lex.response.FulfillmentState
import net.hasanguner.lex.response.LexEventResponse
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LexEventsHandler @Inject constructor(
    private val intentHandlers: Set<@JvmSuppressWildcards LexIntentHandler>
) : Handler<LexEventRequest, LexEventResponse> {

    private val dispatcher: LexEventDispatcher by lazy { DefaultLexEventDispatcher(intentHandlers) }

    private val logger = LoggerFactory.getLogger(javaClass)

    override suspend fun execute(request: LexEventRequest): LexEventResponse =
        try {
            dispatcher.dispatch(request)
        } catch (e: Exception) {
            logger.error("Unable to execute Intent[${request.currentIntent.name}", e)
            LexEventResponse(request.session(), CloseDialogAction(FulfillmentState.FAILED))
        }

}