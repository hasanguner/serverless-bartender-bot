package net.hasanguner.messages.cocktail

import net.hasanguner.messages.SuccessfulOperationResponse

data class FilterCocktailResponse(
    val items: Collection<CocktailDto>
) : SuccessfulOperationResponse()
