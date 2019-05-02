package net.hasanguner.domain.lex

import net.hasanguner.domain.lex.DialogEvent.*
import net.hasanguner.domain.lex.DialogState.*
import net.hasanguner.lex.LexIntentHandler
import net.hasanguner.lex.request.LexEventRequest
import net.hasanguner.lex.response.LexEventResponse
import net.hasanguner.statemachine.StateMachine
import net.hasanguner.statemachine.StateMachine.Companion.withEntry
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class OrderDrinkIntentHandler @Inject constructor(
    private val dialogStateActions: Map<DialogState, @JvmSuppressWildcards Provider<DialogStateAction>>
) : LexIntentHandler {

    override fun handle(event: LexEventRequest): LexEventResponse = handleCodeHook(event)

    private fun handleCodeHook(request: LexEventRequest): LexEventResponse = with(request) {
        val order: CocktailOrder
        val state: DialogState
        var session: Map<String, String> = session()

        //if a slot recognized during intent invocation
        if (session.isEmpty() && currentIntent.slots.values.filterNotNull().isNotEmpty()) {
            order = CocktailOrder.parse(currentIntent)
            state = order.toProperDialogState()
            session = order.writeToSession(session)
                .let { state.writeTo(it) }
        } else {
            order = CocktailOrder.parse(session)
            state = DialogState.parse(session)
        }
        OrderCocktailContext(state, order, currentIntent, session, inputTranscript)
            .let(::takeAction)
            .let { LexEventResponse(it.session, it.dialogAction) }
    }

    private fun takeAction(context: OrderCocktailContext): FinalizedActionResult = with(context) {
        val stateMachine = getStateMachine(context.dialogState)
        val action = dialogStateActions[dialogState]?.get()
            ?: throw IllegalStateException("Proper action not found.")
        val result = action.execute(this)
        stateMachine.evaluate(result.event)
        val newSession = result.session
            .let { stateMachine.currentState.writeTo(it) }
            .let { result.cocktailOrder.writeToSession(it) }

        (result as? FinalizedActionResult)
            ?.copy(session = newSession)
            ?: takeAction(
                OrderCocktailContext(
                    stateMachine.currentState,
                    result.cocktailOrder,
                    intent,
                    newSession,
                    inputTranscript
                )
            )
    }

    private fun getStateMachine(entryState: DialogState = ELICIT_ALCOHOL_PREFERENCE): StateMachine<DialogState, DialogEvent> =
        withEntry(entryState) {
            (shouldMove() from ELICIT_ALCOHOL_PREFERENCE to CONFIRM_ALCOHOL_PREFERENCE).on(ALCOHOL_PREFERENCE_REQUESTED)
            (shouldMove() from CONFIRM_ALCOHOL_PREFERENCE to ELICIT_COCKTAIL_CATEGORY).on(ALCOHOL_PREFERENCE_CONFIRMED)
            (shouldMove() from CONFIRM_ALCOHOL_PREFERENCE to SEARCH_FOR_COCKTAIL).on(COCKTAIL_CATEGORY_CONFIRMED)
            (shouldMove() from ELICIT_COCKTAIL_CATEGORY to CONFIRM_COCKTAIL_CATEGORY).on(COCKTAIL_CATEGORY_REQUESTED)
            (shouldMove() from CONFIRM_COCKTAIL_CATEGORY to CLARIFY_COCKTAIL_CATEGORY).on(CLARIFYING_COCKTAIL_CATEGORY)
            (shouldMove() from CLARIFY_COCKTAIL_CATEGORY to CONFIRM_COCKTAIL_CATEGORY).on(COCKTAIL_CATEGORY_REQUESTED)
            (shouldMove() from CONFIRM_COCKTAIL_CATEGORY to SEARCH_FOR_COCKTAIL).on(COCKTAIL_CATEGORY_CONFIRMED)
            (shouldMove() from SEARCH_FOR_COCKTAIL to CONFIRM_COCKTAIL_CATEGORY).on(COCKTAIL_PREFERENCE_REQUESTED)
            (shouldMove() from SEARCH_FOR_COCKTAIL to DialogState.COCKTAIL_SERVED).on(DialogEvent.COCKTAIL_SERVED)
            (shouldMove() from DialogState.COCKTAIL_SERVED to CONFIRM_REPEAT_ORDER).on(ASKED_FOR_REPEAT_ORDER)
            (shouldMove() from CONFIRM_REPEAT_ORDER to DialogState.COCKTAIL_SERVED).on(DialogEvent.COCKTAIL_SERVED)
            (shouldMove() from CONFIRM_REPEAT_ORDER to ELICIT_ALCOHOL_PREFERENCE).on(REPEAT_ORDER_DECLINED)
        }

    private fun CocktailOrder.toProperDialogState(): DialogState = when {
        alcoholType != null && cocktailCategory == null -> DialogState.ELICIT_COCKTAIL_CATEGORY
        alcoholType != null && cocktailCategory != null -> DialogState.SEARCH_FOR_COCKTAIL
        else -> DialogState.ELICIT_ALCOHOL_PREFERENCE
    }

    override fun supports(intentName: String): Boolean = IntentType.ORDER_DRINK.value == intentName

}
