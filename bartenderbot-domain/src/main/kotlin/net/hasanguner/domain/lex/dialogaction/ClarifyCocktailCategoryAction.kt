package net.hasanguner.domain.lex.dialogaction

import net.hasanguner.domain.cocktail.CocktailCategory
import net.hasanguner.domain.lex.*
import net.hasanguner.lex.response.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClarifyCocktailCategoryAction @Inject constructor() : DialogStateAction {

    override fun execute(context: OrderCocktailContext): ActionResult = with(context) {

        var numberOfRetries = session[LexConstants.SessionAttribute.CLARIFY_CATEGORY_ATTEMPT]?.toInt() ?: 0

        val message: String = when (numberOfRetries++) {
            0 -> LexConstants.Message.SHOW_CATEGORY_OPTIONS_FIRST
            1 -> LexConstants.Message.SHOW_CATEGORY_OPTIONS_SECOND
            else -> LexConstants.Message.SHOW_CATEGORY_OPTIONS_ELSE
        }
        val responseCard = ResponseCard(genericAttachments = listOf(
            GenericAttachment(LexConstants.Message.PICK_CATEGORY_TITLE,
                subTitle = LexConstants.Message.CHOOSE_FAVORITE_SUB_TITLE,
                buttons = drawCategories(numberOfRetries).map { Button(it.description, it.name) }
            ))
        )
        val newSession =
            session + (LexConstants.SessionAttribute.CLARIFY_CATEGORY_ATTEMPT to numberOfRetries.toString())
        val dialogAction = ElicitSlotDialogAction(
            intent.name,
            cocktailOrder.populateSlots(intent.slots),
            LexConstants.Slot.COCKTAIL_CATEGORY,
            Message.plainText(message),
            responseCard
        )
        FinalizedActionResult(DialogEvent.COCKTAIL_CATEGORY_REQUESTED, newSession, cocktailOrder, dialogAction)
    }

    private fun drawCategories(numberOfRetries: Int): Iterable<CocktailCategory> =
        CocktailCategory.values().let {
            if (numberOfRetries % 2 == 0) it.take(5)
            else it.takeLast(5)
        }

    override fun supports(dialogState: DialogState): Boolean =
        DialogState.CLARIFY_COCKTAIL_CATEGORY == dialogState
}