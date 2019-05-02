package net.hasanguner.persistence.cocktail

import com.amazonaws.services.dynamodbv2.datamodeling.*
import net.hasanguner.domain.cocktail.AlcoholType
import net.hasanguner.domain.cocktail.CocktailCategory
import net.hasanguner.domain.cocktail.Ingredient
import net.hasanguner.persistence.cocktail.CocktailEntity.Companion.TABLE_NAME
import net.hasanguner.persistence.converter.IngredientListConverter
import net.hasanguner.persistence.converter.LocalDateTimeConverter
import java.time.LocalDateTime

@DynamoDBTable(tableName = TABLE_NAME)
class CocktailEntity(

    @DynamoDBHashKey
    @field:DynamoDBAttribute
    var cocktailId: String,

    @field:DynamoDBAttribute
    var cocktailName: String,

    @field:DynamoDBIndexHashKey(globalSecondaryIndexName = GSI_NAME)
    @field:DynamoDBAttribute
    @field:DynamoDBTypeConvertedEnum
    var alcoholType: AlcoholType,

    @field:DynamoDBIndexRangeKey(globalSecondaryIndexName = GSI_NAME)
    @field:DynamoDBAttribute
    @field:DynamoDBTypeConvertedEnum
    var category: CocktailCategory,

    @field:DynamoDBAttribute
    var glass: String,

    @field:DynamoDBAttribute
    var imageUrl: String,

    @field:DynamoDBAttribute
    var instructions: String,

    @field:DynamoDBTypeConverted(converter = IngredientListConverter::class)
    var ingredients: List<Ingredient>

) {

    @field:DynamoDBTypeConverted(converter = LocalDateTimeConverter::class)
    @field:DynamoDBAttribute
    var createdAt: LocalDateTime = LocalDateTime.now()

    companion object {
        const val TABLE_NAME = "Cocktail"
        const val GSI_NAME = "alcoholTypeCategoryIndex"
    }
}
