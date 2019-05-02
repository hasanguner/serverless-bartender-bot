package net.hasanguner.domain.lex

interface DialogStateAction {

    fun execute(context: OrderCocktailContext): ActionResult

    infix fun supports(dialogState: DialogState): Boolean

}