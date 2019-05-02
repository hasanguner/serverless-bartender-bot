package net.hasanguner.domain.lex.dialogaction

import net.hasanguner.domain.lex.*
import net.hasanguner.lex.response.ElicitSlotDialogAction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ElicitCocktailCategoryAction @Inject constructor() : DialogStateAction {

    override fun execute(context: OrderCocktailContext): FinalizedActionResult = with(context) {
        val action = ElicitSlotDialogAction(
            intent.name,
            cocktailOrder.populateSlots(intent.slots),
            LexConstants.Slot.COCKTAIL_CATEGORY
        )
        FinalizedActionResult(DialogEvent.COCKTAIL_CATEGORY_REQUESTED, session, cocktailOrder, action)
    }

    override fun supports(dialogState: DialogState): Boolean = DialogState.ELICIT_COCKTAIL_CATEGORY == dialogState

}
