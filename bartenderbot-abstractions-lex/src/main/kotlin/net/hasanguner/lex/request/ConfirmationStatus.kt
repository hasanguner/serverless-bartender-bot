package net.hasanguner.lex.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class ConfirmationStatus(@get:JsonValue val key: String) {
    CONFIRMED("Confirmed"),
    DENIED("Denied"),
    NONE("None");

    companion object {
        @JvmStatic
        @JsonCreator
        fun fromString(key: String): ConfirmationStatus =
            ConfirmationStatus.valueOf(key.toUpperCase())
    }

}