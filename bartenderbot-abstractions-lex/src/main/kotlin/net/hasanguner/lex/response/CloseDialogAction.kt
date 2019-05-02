package net.hasanguner.lex.response

import net.hasanguner.lex.response.DialogActionType.CLOSE

data class CloseDialogAction(
    val fulfillmentState: FulfillmentState,
    override val message: Message? = null,
    override val responseCard: ResponseCard? = null
) : DetailedDialogAction(CLOSE, message, responseCard)