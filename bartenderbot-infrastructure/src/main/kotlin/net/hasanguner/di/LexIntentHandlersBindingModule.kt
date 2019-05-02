package net.hasanguner.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import net.hasanguner.domain.lex.GreetingsIntentHandler
import net.hasanguner.domain.lex.InfoIntentHandler
import net.hasanguner.domain.lex.OrderDrinkIntentHandler
import net.hasanguner.lex.LexIntentHandler

@Module
abstract class LexIntentHandlersBindingModule {

    @Binds
    @IntoSet
    abstract fun provideGreetingsIntentHandler(intentHandler: GreetingsIntentHandler): LexIntentHandler

    @Binds
    @IntoSet
    abstract fun provideInfoIntentHandler(intentHandler: InfoIntentHandler): LexIntentHandler

    @Binds
    @IntoSet
    abstract fun provideOrderDrinkIntentHandler(intentHandler: OrderDrinkIntentHandler): LexIntentHandler


}
