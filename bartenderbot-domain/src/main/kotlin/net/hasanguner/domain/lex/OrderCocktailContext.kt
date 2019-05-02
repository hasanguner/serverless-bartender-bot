package net.hasanguner.domain.lex

import net.hasanguner.lex.request.CurrentIntent

data class OrderCocktailContext(
    val dialogState: DialogState,
    val cocktailOrder: CocktailOrder,
    val intent: CurrentIntent,
    val session: Map<String, String>,
    val inputTranscript: String
)