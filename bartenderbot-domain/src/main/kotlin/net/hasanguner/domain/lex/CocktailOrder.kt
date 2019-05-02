package net.hasanguner.domain.lex

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.hasanguner.domain.cocktail.AlcoholType
import net.hasanguner.domain.cocktail.CocktailCategory
import net.hasanguner.lex.request.CurrentIntent

data class CocktailOrder(
    val alcoholType: AlcoholType? = null,
    val cocktailCategory: CocktailCategory? = null
) {

    @JsonIgnore
    fun isEmpty(): Boolean = this == CocktailOrder()

    fun writeToSession(
        sessionAttributes: Map<String, String>,
        objectMapper: ObjectMapper = jacksonObjectMapper()
    ): Map<String, String> =
        sessionAttributes + (LexConstants.SessionAttribute.CURRENT_ORDER to objectMapper.writeValueAsString(this))

    @Suppress("UNCHECKED_CAST")
    fun populateSlots(
        slots: Map<String, String>,
        objectMapper: ObjectMapper = jacksonObjectMapper()
    ): Map<String, String> =
        slots + (objectMapper.convertValue<Map<String, String?>>(this) as Map<String, String>)

    companion object {
        fun parse(
            sessionAttributes: Map<String, String>,
            objectMapper: ObjectMapper = jacksonObjectMapper()
        ): CocktailOrder =
            sessionAttributes[LexConstants.SessionAttribute.CURRENT_ORDER]
                ?.let { objectMapper.readValue<CocktailOrder>(it) }
                ?: CocktailOrder()

        fun parse(currentIntent: CurrentIntent, objectMapper: ObjectMapper = jacksonObjectMapper()): CocktailOrder =
            currentIntent.slots.let { objectMapper.convertValue(it) }

    }

}
