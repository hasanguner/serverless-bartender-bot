package net.hasanguner.handler

import kotlinx.coroutines.experimental.async
import net.hasanguner.domain.Handler
import net.hasanguner.domain.cocktail.Cocktail
import net.hasanguner.domain.cocktail.CocktailId
import net.hasanguner.domain.cocktail.CocktailRepository
import net.hasanguner.domain.cocktail.Ingredient
import net.hasanguner.messages.SuccessfulOperationResponse
import net.hasanguner.messages.cocktail.CreateCocktailCommand
import net.hasanguner.messages.cocktail.IngredientDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateCocktailHandler @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : Handler<CreateCocktailCommand, SuccessfulOperationResponse> {

    override suspend fun execute(request: CreateCocktailCommand): SuccessfulOperationResponse =
        request.toCocktail()
            .let { async { cocktailRepository.save(request.toCocktail()) } }
            .await()
            .let { SuccessfulOperationResponse() }

    private fun CreateCocktailCommand.toCocktail() =
        Cocktail(CocktailId.create(id), name, alcoholType, category,
            glass, imageUrl, instructions, ingredients.map { it.toIngredient() })

    private fun IngredientDto.toIngredient() = Ingredient(name, measure)

}
