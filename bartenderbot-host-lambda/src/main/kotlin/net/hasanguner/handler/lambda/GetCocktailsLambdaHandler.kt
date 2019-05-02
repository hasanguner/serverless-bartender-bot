package net.hasanguner.handler.lambda

import net.hasanguner.di.DaggerGetCocktailsComponent
import net.hasanguner.handler.GetCocktailsHandler
import net.hasanguner.handler.apigateway.CoAbstractProxyRequestHandler
import net.hasanguner.messages.cocktail.FilterCocktailResponse

class GetCocktailsLambdaHandler : CoAbstractProxyRequestHandler<Unit, FilterCocktailResponse>() {

    private val getCocktailsHandler: GetCocktailsHandler =
        DaggerGetCocktailsComponent.create().getCocktailsHandler

    override suspend fun handle(request: Unit): FilterCocktailResponse =
        getCocktailsHandler.execute(request)

}
