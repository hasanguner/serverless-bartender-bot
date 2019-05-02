package net.hasanguner.di

import dagger.Component
import net.hasanguner.handler.CreateCocktailHandler
import javax.inject.Singleton

@Singleton
@Component(modules = [CocktailRepositoryModule::class])
interface CreateCocktailComponent {

    val createCocktailHandler: CreateCocktailHandler

}