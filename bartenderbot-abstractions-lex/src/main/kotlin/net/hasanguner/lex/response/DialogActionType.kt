package net.hasanguner.lex.response

import com.fasterxml.jackson.annotation.JsonValue

enum class DialogActionType(@get:JsonValue val value: String) {
    DELEGATE("Delegate"),
    CLOSE("Close"),
    CONFIRM_INTENT("ConfirmIntent"),
    ELICIT_INTENT("ElicitIntent"),
    ELICIT_SLOT("ElicitSlot")
}
