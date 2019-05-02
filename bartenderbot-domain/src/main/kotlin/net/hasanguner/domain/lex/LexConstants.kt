package net.hasanguner.domain.lex

object LexConstants {

    object Slot {
        const val ALCOHOL_TYPE = "alcoholType"
        const val COCKTAIL_CATEGORY = "cocktailCategory"
    }

    object SessionAttribute {
        const val CLARIFY_CATEGORY_ATTEMPT = "clarifyCategoryAttempt"
        const val CURRENT_ORDER = "currentOrder"
        const val DIALOG_STATE = "dialogState"
        const val LAST_ORDERED_ITEM = "lastOrderedItem"
        const val LAST_ORDERED_ITEM_ID = "lastOrderedItemId"
    }

    object Message {
        const val GREETINGS = "Hi. How can i help you ?"
        const val INFO = "I'm working here as a bartender. I can make you a nice cocktail." +
                " I can even teach you how to do it! How can i help you ?"
        const val ELICIT_ALCOHOL_PREFERENCE = "Would you like to drink an alcoholic cocktail ?"
        const val SHOW_CATEGORY_OPTIONS_FIRST = "Here are the categories."
        const val SHOW_CATEGORY_OPTIONS_SECOND = "Let's try these."
        const val SHOW_CATEGORY_OPTIONS_ELSE = "You need to select one."
        const val PICK_CATEGORY_TITLE = "Pick a category"
        const val CHOOSE_FAVORITE_SUB_TITLE = "Choose your favorite"
        const val PICK_ANOTHER_CATEGORY = "Sorry, unfortunately we don't have a cocktail in this category." +
                " Would you pick another one ?"

        const val SERVE_COCKTAIL_TEMPLATE = "Here you are. Enjoy your #{0}!"
        const val ASK_FOR_REPEAT_ORDER_TEMPLATE = "Would like to have #{0} again ?"

        fun build(template: String, vararg options: String): String {
            var message = template
            options.forEachIndexed { index, s -> message = message.replace("#{$index}", s) }
            return message
        }
    }

}