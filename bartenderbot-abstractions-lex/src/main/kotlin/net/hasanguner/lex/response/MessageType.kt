package net.hasanguner.lex.response

import com.fasterxml.jackson.annotation.JsonValue

enum class MessageType(@get:JsonValue val value: String) {
    PLAIN_TEXT("PlainText"),
    SSML("SSML"),
    CUSTOM_PAYLOAD("CustomPayload")
}
