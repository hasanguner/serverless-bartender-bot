package net.hasanguner.domain.cocktail

data class Cocktail(
    val cocktailId: CocktailId,
    val cocktailName: String,
    val alcoholType: AlcoholType,
    val category: CocktailCategory,
    val glass: String,
    val imageUrl: String,
    val instructions: String,
    val ingredients: List<Ingredient>
)
