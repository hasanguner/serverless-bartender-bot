package net.hasanguner.lex.response

data class GenericAttachment(
    val title: String,
    val subTitle: String? = null,
    val imageUrl: String? = null,
    val attachmentLinkUrl: String? = null,
    val buttons: List<Button>? = null
)
