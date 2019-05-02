package net.hasanguner.di

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import dagger.Module
import dagger.Provides
import net.hasanguner.domain.cocktail.CocktailRepository
import net.hasanguner.persistence.DynamoDBInitializer
import net.hasanguner.persistence.DynamoDBProperties
import net.hasanguner.persistence.cocktail.CocktailEntityConverter
import net.hasanguner.persistence.cocktail.CocktailRepositoryImpl
import javax.inject.Singleton

@Module
class CocktailRepositoryModule {

    @get:Provides
    @get:Singleton
    val dynamoDBProperties: DynamoDBProperties =
        DynamoDBProperties().apply { region = System.getenv("aws_region") }

    @Provides
    @Singleton
    fun provideDynamoDBMapper(dynamoDBProperties: DynamoDBProperties): DynamoDBMapper =
        DynamoDBInitializer().getDynamoDBMapper(dynamoDBProperties)

    @Provides
    @Singleton
    fun provideCocktailRepository(
        mapper: DynamoDBMapper,
        entityConverter: CocktailEntityConverter
    ): CocktailRepository =
        CocktailRepositoryImpl(mapper, entityConverter)

}
