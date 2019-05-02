package net.hasanguner.lex.response

import net.hasanguner.lex.response.DialogActionType.CONFIRM_INTENT

data class ConfirmDialogAction(
    val intentName: String,
    val slots: Map<String, String>,
    override val message: Message? = null,
    override val responseCard: ResponseCard? = null
) : DetailedDialogAction(CONFIRM_INTENT, message, responseCard)