package net.hasanguner.domain.lex.dialogaction

import net.hasanguner.domain.lex.*
import net.hasanguner.lex.response.ConfirmDialogAction
import net.hasanguner.lex.response.Message
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ElicitAlcoholPreferenceAction @Inject constructor() : DialogStateAction {

    override fun execute(context: OrderCocktailContext): ActionResult = with(context) {
        val dialogAction = ConfirmDialogAction(
            intent.name,
            intent.slots,
            Message.plainText(LexConstants.Message.ELICIT_ALCOHOL_PREFERENCE)
        )

        FinalizedActionResult(DialogEvent.ALCOHOL_PREFERENCE_REQUESTED, session, cocktailOrder, dialogAction)
    }

    override fun supports(dialogState: DialogState): Boolean = DialogState.ELICIT_ALCOHOL_PREFERENCE == dialogState

}
