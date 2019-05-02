package net.hasanguner.domain

import com.fasterxml.jackson.databind.ObjectMapper
import net.hasanguner.domain.lex.CocktailOrder
import spock.lang.Specification

class SampleSpec extends Specification {

    def sample() {
        given:
        def json = """{"alcoholType" : null, "cocktailCategory" : "ORDINARY_DRINK"}"""
        when:
        def order = new ObjectMapper().readValue(json, CocktailOrder.class)
        then:
        println order
    }
}
