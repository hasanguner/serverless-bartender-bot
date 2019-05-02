package net.hasanguner.lex.request

data class LexEventRequest(
    val messageVersion: String,
    val invocationSource: InvocationSource,
    val userId: String,
    val sessionAttributes: Map<String, String>?,
    val requestAttributes: String?,
    val bot: Bot,
    val outputDialogMode: OutputDialogMode,
    val currentIntent: CurrentIntent,
    val inputTranscript: String
) {

    fun session(): Map<String, String> = sessionAttributes ?: emptyMap()
}