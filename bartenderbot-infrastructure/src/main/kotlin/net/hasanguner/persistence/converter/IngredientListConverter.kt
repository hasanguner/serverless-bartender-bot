package net.hasanguner.persistence.converter

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.hasanguner.domain.cocktail.Ingredient

class IngredientListConverter : DynamoDBTypeConverter<String, List<Ingredient>> {

    override fun unconvert(content: String): List<Ingredient> = jacksonObjectMapper().readValue(content)

    override fun convert(ingredients: List<Ingredient>): String = jacksonObjectMapper().writeValueAsString(ingredients)
}