package net.hasanguner.persistence.cocktail

import net.hasanguner.domain.EntityConverter
import net.hasanguner.domain.cocktail.Cocktail
import net.hasanguner.domain.cocktail.CocktailId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailEntityConverter @Inject constructor() : EntityConverter<CocktailEntity, Cocktail> {

    @Suppress("USELESS_ELVIS")
    override fun convertToDomainObject(entity: CocktailEntity) = with(entity) {
        Cocktail(
            CocktailId.parse(cocktailId),
            cocktailName,
            alcoholType,
            category,
            glass,
            imageUrl,
            instructions ?: "",//DO NOT REMOVE!
            ingredients
        )
    }

    override fun convertToEntity(domainObject: Cocktail) = with(domainObject) {
        CocktailEntity(
            cocktailId.value,
            cocktailName,
            alcoholType,
            category,
            glass,
            imageUrl,
            instructions,
            ingredients
        )
    }

}