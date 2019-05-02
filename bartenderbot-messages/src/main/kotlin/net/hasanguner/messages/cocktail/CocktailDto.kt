package net.hasanguner.messages.cocktail

import net.hasanguner.domain.cocktail.AlcoholType
import net.hasanguner.domain.cocktail.CocktailCategory

data class CocktailDto(
    val cocktailId: String,
    val cocktailName: String,
    val alcoholType: AlcoholType,
    val category: CocktailCategory,
    val glass: String,
    val imageUrl: String,
    val instructions: String
)