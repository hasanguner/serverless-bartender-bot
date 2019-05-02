package net.hasanguner.lex.request

data class CurrentIntent(
    val name: String,
    val slots: Map<String, String>,
    val slotDetails: Map<String, *>,
    val confirmationStatus: ConfirmationStatus
)