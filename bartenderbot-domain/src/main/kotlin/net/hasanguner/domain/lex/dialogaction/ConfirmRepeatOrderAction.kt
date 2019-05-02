package net.hasanguner.domain.lex.dialogaction

import net.hasanguner.domain.cocktail.Cocktail
import net.hasanguner.domain.cocktail.CocktailId
import net.hasanguner.domain.cocktail.CocktailRepository
import net.hasanguner.domain.lex.*
import net.hasanguner.lex.request.ConfirmationStatus
import net.hasanguner.lex.response.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfirmRepeatOrderAction @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : DialogStateAction {

    override fun execute(context: OrderCocktailContext): ActionResult = with(context) {
        session.takeIf { intent.confirmationStatus == ConfirmationStatus.CONFIRMED }
            ?.let { it[LexConstants.SessionAttribute.LAST_ORDERED_ITEM_ID] }
            ?.let { CocktailId.parse(it) }
            ?.let { cocktailRepository.findByCocktailId(it) }
            ?.let { buildCloseDialogActionResult(this, it) }
            ?: buildRepeatOrderDeclinedActionResult()
    }

    private fun buildRepeatOrderDeclinedActionResult() =
        ContinuousActionResult(DialogEvent.REPEAT_ORDER_DECLINED, emptyMap(), CocktailOrder())

    private fun buildCloseDialogActionResult(
        context: OrderCocktailContext,
        cocktail: Cocktail
    ): ActionResult = with(context) {
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
        FinalizedActionResult(DialogEvent.COCKTAIL_SERVED, session, cocktailOrder, action)
    }

    override fun supports(dialogState: DialogState): Boolean = DialogState.CONFIRM_ALCOHOL_PREFERENCE == dialogState

}
