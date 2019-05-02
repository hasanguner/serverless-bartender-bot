package net.hasanguner.di

import dagger.Component
import net.hasanguner.handler.FilterCocktailHandler
import javax.inject.Singleton

@Singleton
@Component(modules = [CocktailRepositoryModule::class])
interface FilterCocktailComponent {

    val filterCocktailHandler: FilterCocktailHandler

}