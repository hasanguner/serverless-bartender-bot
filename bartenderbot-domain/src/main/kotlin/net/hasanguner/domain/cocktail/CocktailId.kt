package net.hasanguner.domain.cocktail

import java.util.*

data class CocktailId(val value: String) {
    companion object {
        @JvmStatic
        fun create(id: Int): CocktailId =
            CocktailId(UUID.nameUUIDFromBytes("$id".toByteArray()).toString())

        @JvmStatic
        fun parse(value: String): CocktailId =
            CocktailId(value)
    }
}
