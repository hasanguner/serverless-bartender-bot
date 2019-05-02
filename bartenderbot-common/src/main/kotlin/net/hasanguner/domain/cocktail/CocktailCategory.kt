package net.hasanguner.domain.cocktail

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class CocktailCategory(@get:JsonValue val description: String) {

    ORDINARY_DRINK("Ordinary Drink"),
    MILK_FLOAT_SHAKE("Milk / Float / Shake"),
    COCOA("Cocoa"),
    SHOT("Shot"),
    COFFEE_TEA("Coffee / Tea"),
    HOMEMADE_LIQUEUR("Homemade Liqueur"),
    PUNCH_PARTY_DRINK("Punch / Party Drink"),
    BEER("Beer"),
    SOFT_DRINK_SODA("Soft Drink / Soda"),
    COCKTAIL("Cocktail"),
    UNKNOWN("Other/Unknown");

    companion object {
        @JvmStatic
        @JsonCreator
        fun fromString(key: String): CocktailCategory =
            values().firstOrNull { key.equals(it.description, ignoreCase = true) }
                ?: values().first { key.equals(it.name, ignoreCase = true) }
    }

}
