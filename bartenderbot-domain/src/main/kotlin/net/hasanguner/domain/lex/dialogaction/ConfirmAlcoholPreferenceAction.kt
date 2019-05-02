package net.hasanguner.domain.lex.dialogaction

import net.hasanguner.domain.cocktail.AlcoholType
import net.hasanguner.domain.lex.*
import net.hasanguner.domain.lex.DialogEvent.*
import net.hasanguner.lex.request.ConfirmationStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfirmAlcoholPreferenceAction @Inject constructor() : DialogStateAction {

    override fun execute(context: OrderCocktailContext): ActionResult = with(context) {
        when (intent.confirmationStatus) {
            ConfirmationStatus.CONFIRMED -> AlcoholType.ALCOHOLIC
            ConfirmationStatus.DENIED -> AlcoholType.NONALCOHOLIC
            else -> AlcoholType.OPTIONAL
        }.let {
            cocktailOrder.copy(alcoholType = it)
        }.let {
            val event = if (cocktailOrder.cocktailCategory != null)
                COCKTAIL_CATEGORY_CONFIRMED else ALCOHOL_PREFERENCE_CONFIRMED
            ContinuousActionResult(event, session, it)
        }
    }

    override fun supports(dialogState: DialogState): Boolean = DialogState.CONFIRM_ALCOHOL_PREFERENCE == dialogState

}
