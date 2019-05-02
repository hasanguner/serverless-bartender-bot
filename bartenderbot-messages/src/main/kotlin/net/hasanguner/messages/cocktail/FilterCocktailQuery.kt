package net.hasanguner.messages.cocktail

import net.hasanguner.domain.cocktail.AlcoholType
import net.hasanguner.domain.cocktail.CocktailCategory

data class FilterCocktailQuery(
    val alcoholType: AlcoholType,
    val cocktailCategory: CocktailCategory
)
