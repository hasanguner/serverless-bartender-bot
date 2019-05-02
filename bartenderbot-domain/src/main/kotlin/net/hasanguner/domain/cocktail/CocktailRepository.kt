package net.hasanguner.domain.cocktail

interface CocktailRepository {

    fun save(cocktail: Cocktail)

    fun save(cocktails: List<Cocktail>)

    fun findAll(): List<Cocktail>

    fun findByCocktailId(cocktailId: CocktailId): Cocktail?

    fun filter(alcoholType: AlcoholType, cocktailCategory: CocktailCategory): List<Cocktail>

    fun delete(cocktailId: CocktailId)
}