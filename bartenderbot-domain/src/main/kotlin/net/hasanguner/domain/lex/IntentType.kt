package net.hasanguner.domain.lex

import com.fasterxml.jackson.annotation.JsonValue

enum class IntentType(@get:JsonValue val value: String) {
    INFO("Info"),
    GREETINGS("Greetings"),
    ORDER_DRINK("OrderDrink")
}
