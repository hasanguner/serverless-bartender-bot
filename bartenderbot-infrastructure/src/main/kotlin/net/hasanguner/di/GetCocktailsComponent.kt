package net.hasanguner.di

import dagger.Component
import net.hasanguner.handler.GetCocktailsHandler
import javax.inject.Singleton

@Singleton
@Component(modules = [CocktailRepositoryModule::class])
interface GetCocktailsComponent {

    val getCocktailsHandler: GetCocktailsHandler

}