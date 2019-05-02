package net.hasanguner.lex.response

data class LexEventResponse(
    val sessionAttributes: Map<String, String>,
    val dialogAction: DialogAction
)