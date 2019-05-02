package net.hasanguner.lex.response

abstract class DetailedDialogAction(
    override val type: DialogActionType,
    open val message: Message? = null,
    open val responseCard: ResponseCard? = null
) : DialogAction(type)