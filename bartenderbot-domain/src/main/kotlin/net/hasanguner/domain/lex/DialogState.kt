package net.hasanguner.domain.lex

enum class DialogState {
    ELICIT_ALCOHOL_PREFERENCE,
    CONFIRM_ALCOHOL_PREFERENCE,
    ELICIT_COCKTAIL_CATEGORY,
    CONFIRM_COCKTAIL_CATEGORY,
    CLARIFY_COCKTAIL_CATEGORY,
    SEARCH_FOR_COCKTAIL,
    COCKTAIL_SERVED,
    CONFIRM_REPEAT_ORDER;

    fun writeTo(sessionAttributes: Map<String, String>): Map<String, String> =
        sessionAttributes + (LexConstants.SessionAttribute.DIALOG_STATE to name)

    companion object {
        fun parse(sessionAttributes: Map<String, String>) =
            sessionAttributes[LexConstants.SessionAttribute.DIALOG_STATE]
                ?.let { DialogState.valueOf(it) }
                ?: ELICIT_ALCOHOL_PREFERENCE
    }

}