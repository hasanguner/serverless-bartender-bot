package net.hasanguner.domain.lex.dialogaction

import net.hasanguner.domain.lex.*
import net.hasanguner.lex.response.ConfirmDialogAction
import net.hasanguner.lex.response.Message
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AskForRepeatOrderAction @Inject constructor() : DialogStateAction {

    override fun execute(context: OrderCocktailContext): ActionResult = with(context) {
        val lastOrderedItem = session[LexConstants.SessionAttribute.LAST_ORDERED_ITEM] ?: throw IllegalStateException()
        val dialogAction = ConfirmDialogAction(
            intent.name,
            intent.slots,
            Message.plainText(
                LexConstants.Message.build(
                    LexConstants.Message.ASK_FOR_REPEAT_ORDER_TEMPLATE,
                    lastOrderedItem
                )
            )
        )
        FinalizedActionResult(DialogEvent.ASKED_FOR_REPEAT_ORDER, session, cocktailOrder, dialogAction)
    }

    override fun supports(dialogState: DialogState): Boolean = DialogState.COCKTAIL_SERVED == dialogState

}
