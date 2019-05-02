package net.hasanguner.handler

import kotlinx.coroutines.experimental.async
import net.hasanguner.converter.CocktailConverter
import net.hasanguner.domain.Handler
import net.hasanguner.domain.cocktail.CocktailRepository
import net.hasanguner.messages.cocktail.FilterCocktailQuery
import net.hasanguner.messages.cocktail.FilterCocktailResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilterCocktailHandler @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    private val cocktailConverter: CocktailConverter
) : Handler<FilterCocktailQuery, FilterCocktailResponse> {

    override suspend fun execute(request: FilterCocktailQuery): FilterCocktailResponse = with(request) {
        async { cocktailRepository.filter(alcoholType, cocktailCategory) }
            .await()
            .let { cocktailConverter.convert(it) }
            .let { FilterCocktailResponse(it) }
    }

}
