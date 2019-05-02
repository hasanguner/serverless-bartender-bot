package net.hasanguner.lex.response

import net.hasanguner.lex.response.MessageType.*

data class Message(
    val contentType: MessageType,
    val content: String
) {

    companion object {

        @JvmStatic
        fun plainText(content: String): Message = Message(PLAIN_TEXT, content)

        @JvmStatic
        fun ssml(content: String): Message = Message(SSML, content)

        @JvmStatic
        fun customPayload(content: String): Message = Message(CUSTOM_PAYLOAD, content)

    }
}
