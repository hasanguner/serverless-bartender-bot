package net.hasanguner.domain.lex.dialogaction

import net.hasanguner.domain.cocktail.Cocktail
import net.hasanguner.domain.cocktail.CocktailRepository
import net.hasanguner.domain.lex.*
import net.hasanguner.lex.response.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchForCocktailAction @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : DialogStateAction {

    override fun execute(context: OrderCocktailContext): ActionResult = with(context) {
        cocktailRepository.filter(cocktailOrder.alcoholType!!, cocktailOrder.cocktailCategory!!)
            .shuffled()
            .firstOrNull()
            ?.let { buildCloseDialogActionResult(this, it) }
            ?: buildElicitCategoryActionResult(this)
    }

    private fun buildCloseDialogActionResult(
        context: OrderCocktailContext,
        cocktail: Cocktail
    ): ActionResult = with(context) {
        val newSession = session.toMutableMap().apply {
            put(LexConstants.SessionAttribute.LAST_ORDERED_ITEM, cocktail.cocktailName)
            put(LexConstants.SessionAttribute.LAST_ORDERED_ITEM_ID, cocktail.cocktailId.value)
        }
        val action = CloseDialogAction(
            FulfillmentState.FULFILLED,
            Message.plainText(
                LexConstants.Message.build(
                    LexConstants.Message.SERVE_COCKTAIL_TEMPLATE,
                    cocktail.cocktailName
                )
            ),
            ResponseCard(
                genericAttachments = listOf(
                    GenericAttachment(
                        title = cocktail.cocktailName,
                        imageUrl = cocktail.imageUrl
                    )
                )
            )
        )
        FinalizedActionResult(DialogEvent.COCKTAIL_SERVED, newSession, cocktailOrder, action)
    }

    private fun buildElicitCategoryActionResult(context: OrderCocktailContext): ActionResult = with(context) {
        val newSession = session.toMutableMap()
            .apply { remove(LexConstants.SessionAttribute.CLARIFY_CATEGORY_ATTEMPT) }
        val newCocktailOrder = cocktailOrder.copy(cocktailCategory = null)
        val action = ElicitSlotDialogAction(
            intent.name,
            newCocktailOrder.populateSlots(intent.slots),
            LexConstants.Slot.COCKTAIL_CATEGORY,
            Message.plainText(LexConstants.Message.PICK_ANOTHER_CATEGORY)
        )
        FinalizedActionResult(DialogEvent.COCKTAIL_PREFERENCE_REQUESTED, newSession, newCocktailOrder, action)
    }

    override fun supports(dialogState: DialogState): Boolean = DialogState.SEARCH_FOR_COCKTAIL == dialogState


}
