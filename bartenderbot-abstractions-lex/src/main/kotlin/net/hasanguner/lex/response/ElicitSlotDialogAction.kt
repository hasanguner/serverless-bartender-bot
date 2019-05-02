package net.hasanguner.lex.response

import net.hasanguner.lex.response.DialogActionType.ELICIT_SLOT

data class ElicitSlotDialogAction(
    val intentName: String,
    val slots: Map<String, String>,
    val slotToElicit: String,
    override val message: Message? = null,
    override val responseCard: ResponseCard? = null
) : DetailedDialogAction(ELICIT_SLOT, message, responseCard)