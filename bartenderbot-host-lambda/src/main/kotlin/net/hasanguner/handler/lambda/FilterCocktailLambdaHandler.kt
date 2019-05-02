package net.hasanguner.handler.lambda

import net.hasanguner.di.DaggerFilterCocktailComponent
import net.hasanguner.handler.FilterCocktailHandler
import net.hasanguner.handler.apigateway.CoAbstractProxyRequestHandler
import net.hasanguner.messages.cocktail.FilterCocktailQuery
import net.hasanguner.messages.cocktail.FilterCocktailResponse

class FilterCocktailLambdaHandler : CoAbstractProxyRequestHandler<FilterCocktailQuery, FilterCocktailResponse>() {

    private val filterCocktailHandler: FilterCocktailHandler =
        DaggerFilterCocktailComponent.create().filterCocktailHandler

    override suspend fun handle(request: FilterCocktailQuery): FilterCocktailResponse =
        filterCocktailHandler.execute(request)

}
