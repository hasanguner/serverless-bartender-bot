package net.hasanguner.lex.response

import net.hasanguner.lex.response.DialogActionType.DELEGATE

data class DelegateDialogAction(
    val slots: Map<String, String>
) : DialogAction(DELEGATE)