package net.hasanguner.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import net.hasanguner.domain.lex.DialogState
import net.hasanguner.domain.lex.DialogStateAction
import net.hasanguner.domain.lex.dialogaction.*

@Module
abstract class DialogStateActionsBindingModule {

    @Binds
    @IntoMap
    @DialogStateMapKey(DialogState.ELICIT_ALCOHOL_PREFERENCE)
    abstract fun provideElicitAlcoholPreferenceAction(action: ElicitAlcoholPreferenceAction): DialogStateAction

    @Binds
    @IntoMap
    @DialogStateMapKey(DialogState.CONFIRM_ALCOHOL_PREFERENCE)
    abstract fun provideConfirmAlcoholPreferenceStateAction(action: ConfirmAlcoholPreferenceAction): DialogStateAction

    @Binds
    @IntoMap
    @DialogStateMapKey(DialogState.ELICIT_COCKTAIL_CATEGORY)
    abstract fun provideElicitCocktailCategoryAction(action: ElicitCocktailCategoryAction): DialogStateAction

    @Binds
    @IntoMap
    @DialogStateMapKey(DialogState.CONFIRM_COCKTAIL_CATEGORY)
    abstract fun provideConfirmCocktailCategoryStateAction(action: ConfirmCocktailCategoryAction): DialogStateAction

    @Binds
    @IntoMap
    @DialogStateMapKey(DialogState.CLARIFY_COCKTAIL_CATEGORY)
    abstract fun provideClarifyCocktailCategoryStateAction(action: ClarifyCocktailCategoryAction): DialogStateAction

    @Binds
    @IntoMap
    @DialogStateMapKey(DialogState.SEARCH_FOR_COCKTAIL)
    abstract fun provideSearchForCocktailStateAction(action: SearchForCocktailAction): DialogStateAction

    @Binds
    @IntoMap
    @DialogStateMapKey(DialogState.COCKTAIL_SERVED)
    abstract fun provideAskForRepatOrderStateAction(action: AskForRepeatOrderAction): DialogStateAction

    @Binds
    @IntoMap
    @DialogStateMapKey(DialogState.CONFIRM_REPEAT_ORDER)
    abstract fun provideConfirmRepeatOrderStateAction(action: ConfirmRepeatOrderAction): DialogStateAction

}
