package net.hasanguner.handler.lambda

import net.hasanguner.di.DaggerCreateCocktailComponent
import net.hasanguner.handler.CreateCocktailHandler
import net.hasanguner.handler.apigateway.CoAbstractProxyRequestHandler
import net.hasanguner.messages.SuccessfulOperationResponse
import net.hasanguner.messages.cocktail.CreateCocktailCommand

class CreateCocktailLambdaHandler : CoAbstractProxyRequestHandler<CreateCocktailCommand, SuccessfulOperationResponse>() {

    private val createCocktailHandler: CreateCocktailHandler =
        DaggerCreateCocktailComponent.create().createCocktailHandler

    override suspend fun handle(request: CreateCocktailCommand): SuccessfulOperationResponse =
        createCocktailHandler.execute(request)
}
