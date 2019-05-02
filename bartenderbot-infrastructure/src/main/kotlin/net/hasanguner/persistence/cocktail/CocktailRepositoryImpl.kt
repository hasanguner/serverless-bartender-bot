package net.hasanguner.persistence.cocktail

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import net.hasanguner.domain.cocktail.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CocktailRepositoryImpl @Inject constructor(
    private val mapper: DynamoDBMapper,
    private val entityConverter: CocktailEntityConverter
) : CocktailRepository {

    override fun save(cocktail: Cocktail) =
        cocktail.let { entityConverter.convertToEntity(it) }
            .run { mapper.save(this) }

    override fun save(cocktails: List<Cocktail>) =
        cocktails.let { entityConverter.convertToEntity(it) }
            .run { mapper.save(this) }

    override fun findAll(): List<Cocktail> =
        mapper.scan(CocktailEntity::class.java, DynamoDBScanExpression())
            .let { entityConverter.convertToDomainObject(it) }

    override fun findByCocktailId(cocktailId: CocktailId): Cocktail? =
        mapper.load(CocktailEntity::class.java, cocktailId.value)
            ?.let { entityConverter.convertToDomainObject(it) }

    override fun filter(alcoholType: AlcoholType, cocktailCategory: CocktailCategory): List<Cocktail> =
        DynamoDBQueryExpression<CocktailEntity>()
            .withIndexName(CocktailEntity.GSI_NAME)
            .withConsistentRead(false)
            .withKeyConditionExpression("alcoholType = :alcoholType and category = :category")
            .withExpressionAttributeValues(
                mapOf(
                    ":alcoholType" to AttributeValue(alcoholType.name),
                    ":category" to AttributeValue(cocktailCategory.name)
                )
            )
            .let { mapper.query(CocktailEntity::class.java, it) }
            .let { entityConverter.convertToDomainObject(it) }

    override fun delete(cocktailId: CocktailId): Unit =
        mapper.load(CocktailEntity::class.java, cocktailId.value)
            ?.let { mapper.delete(it) }
            ?: throw IllegalArgumentException("There is no cocktail associated with CocktailId[${cocktailId.value}]")

}
