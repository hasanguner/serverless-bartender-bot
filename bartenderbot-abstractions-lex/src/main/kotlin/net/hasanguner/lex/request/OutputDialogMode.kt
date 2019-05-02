package net.hasanguner.lex.request

import com.fasterxml.jackson.annotation.JsonValue

enum class OutputDialogMode(@get:JsonValue val value: String) {
    TEXT("Text"),
    VOICE("Voice")
}