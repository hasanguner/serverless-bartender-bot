package net.hasanguner.lex.response

import net.hasanguner.lex.response.DialogActionType.ELICIT_INTENT

data class ElicitIntentDialogAction(
    override val message: Message? = null,
    override val responseCard: ResponseCard? = null
) : DetailedDialogAction(ELICIT_INTENT, message, responseCard)