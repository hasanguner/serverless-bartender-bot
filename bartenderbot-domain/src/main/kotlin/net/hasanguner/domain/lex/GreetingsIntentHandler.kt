package net.hasanguner.domain.lex

import net.hasanguner.lex.LexIntentHandler
import net.hasanguner.lex.request.LexEventRequest
import net.hasanguner.lex.response.ElicitIntentDialogAction
import net.hasanguner.lex.response.LexEventResponse
import net.hasanguner.lex.response.Message
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GreetingsIntentHandler @Inject constructor() : LexIntentHandler {

    override fun handle(event: LexEventRequest): LexEventResponse =
        LexEventResponse(
            event.session(),
            ElicitIntentDialogAction(Message.plainText(LexConstants.Message.GREETINGS))
        )

    override fun supports(intentName: String): Boolean = IntentType.GREETINGS.value == intentName

}