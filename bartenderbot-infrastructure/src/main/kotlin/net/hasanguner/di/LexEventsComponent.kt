package net.hasanguner.di

import dagger.Component
import net.hasanguner.handler.LexEventsHandler
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        LexIntentHandlersBindingModule::class,
        DialogStateActionsBindingModule::class,
        CocktailRepositoryModule::class]
)
interface LexEventsComponent {

    val lexEventsHandler: LexEventsHandler

}