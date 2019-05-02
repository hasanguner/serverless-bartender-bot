package net.hasanguner.converter

import net.hasanguner.domain.Converter
import net.hasanguner.domain.cocktail.Cocktail
import net.hasanguner.messages.cocktail.CocktailDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailConverter @Inject constructor() : Converter<Cocktail, CocktailDto> {

    override fun convert(content: Cocktail): CocktailDto = with(content) {
        CocktailDto(
            cocktailId.value,
            cocktailName,
            alcoholType,
            category,
            glass,
            imageUrl,
            instructions
        )
    }

}