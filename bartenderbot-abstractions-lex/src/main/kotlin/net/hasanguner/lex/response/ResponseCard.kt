package net.hasanguner.lex.response

data class ResponseCard(
    val version: Int = VERSION_1,
    val contentType: String = CARD_RESPONSE_TYPE,
    val genericAttachments: List<GenericAttachment>
) {
    companion object {
        const val VERSION_1 = 1
        const val CARD_RESPONSE_TYPE = "application/vnd.amazonaws.card.generic"
    }
}
