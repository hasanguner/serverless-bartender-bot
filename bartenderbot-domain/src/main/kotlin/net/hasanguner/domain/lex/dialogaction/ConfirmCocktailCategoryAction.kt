package net.hasanguner.domain.lex.dialogaction

import net.hasanguner.domain.cocktail.CocktailCategory
import net.hasanguner.domain.lex.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfirmCocktailCategoryAction @Inject constructor() : DialogStateAction {

    override fun execute(context: OrderCocktailContext): ActionResult = with(context) {
        val category = intent.slots[LexConstants.Slot.COCKTAIL_CATEGORY]
        if (category == null) {
            ContinuousActionResult(
                DialogEvent.CLARIFYING_COCKTAIL_CATEGORY,
                session,
                cocktailOrder
            )
        } else {
            val newCocktailOrder = cocktailOrder.copy(cocktailCategory = CocktailCategory.valueOf(category))
            ContinuousActionResult(
                DialogEvent.COCKTAIL_CATEGORY_CONFIRMED,
                session,
                newCocktailOrder
            )
        }
    }

    override fun supports(dialogState: DialogState): Boolean = DialogState.CONFIRM_COCKTAIL_CATEGORY == dialogState

}
