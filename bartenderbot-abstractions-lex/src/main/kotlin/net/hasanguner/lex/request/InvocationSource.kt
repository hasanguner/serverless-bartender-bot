package net.hasanguner.lex.request

import com.fasterxml.jackson.annotation.JsonValue

enum class InvocationSource(@get:JsonValue val value: String) {
    FULFILLMENT_CODE_HOOK("FulfillmentCodeHook"),
    DIALOG_CODE_HOOK("DialogCodeHook")
}
