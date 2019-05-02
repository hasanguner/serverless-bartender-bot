package net.hasanguner.lex.response

import com.fasterxml.jackson.annotation.JsonValue

enum class FulfillmentState(@get:JsonValue val value: String) {
    FULFILLED("Fulfilled"),
    FAILED("Failed")
}
