package net.hasanguner.domain.lex

import net.hasanguner.lex.response.DialogAction

sealed class ActionResult(
    open val event: DialogEvent,
    open val session: Map<String, String>,
    open val cocktailOrder: CocktailOrder
)

data class ContinuousActionResult(
    override val event: DialogEvent,
    override val session: Map<String, String>,
    override val cocktailOrder: CocktailOrder
) : ActionResult(event, session, cocktailOrder)

data class FinalizedActionResult(
    override val event: DialogEvent,
    override val session: Map<String, String>,
    override val cocktailOrder: CocktailOrder,
    val dialogAction: DialogAction
) : ActionResult(event, session, cocktailOrder)