package net.hasanguner.persistence.cocktail

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import net.hasanguner.domain.cocktail.*
import spock.lang.Specification
import spock.lang.Unroll

@spock.lang.Ignore
class CocktailRepositorySpec extends Specification {

    private CocktailRepository cocktailRepository

    def setup() {
        def dynamoDBMapper = new DynamoDBMapper(
                AmazonDynamoDBClientBuilder.standard()
                        .withCredentials(new ProfileCredentialsProvider("aws-personal-account"))
                        .withRegion(Regions.fromName("eu-west-1"))
                        .build())

        def entityConverter = new CocktailEntityConverter()
        cocktailRepository = new CocktailRepositoryImpl(dynamoDBMapper, entityConverter)
    }

    def "cocktail save operation should succeed"() {
        given:
        def cocktail = "create sample cocktail"()
        when:
        cocktailRepository.save(cocktail)
        then:
        println "Save operation succeed : $cocktail"
    }

    def "find by cocktailId attempt should succeed"() {
        given:
        def cocktail = "create sample cocktail"()
        cocktailRepository.save(cocktail)
        when:
        def result = cocktailRepository.findByCocktailId(cocktail.cocktailId)
        println "Cocktail : $result"
        then:
        result.cocktailId == cocktail.cocktailId
    }

    def "find all cocktails attempt should succeed"() {
        given:
        def cocktail = "create sample cocktail"()
        cocktailRepository.save(cocktail)
        when:
        def cocktails = cocktailRepository.findAll()
        println "Cocktails : $cocktails"
        then:
        cocktails*.cocktailName.contains(cocktail.cocktailName)
    }

    def "delete cocktail should succeed"() {
        given:
        def cocktail = "create sample cocktail"()
        cocktailRepository.save(cocktail)
        when:
        cocktailRepository.delete(cocktail.cocktailId)
        then:
        cocktailRepository.findByCocktailId(cocktail.cocktailId) == null
    }

    @Unroll
    def "find cocktails by alcohol type[#alcoholType] and category[#category] should return cocktail #cocktailName"() {
        setup:
        cocktailRepository.save("create sample cocktail"())
        expect:
        def cocktails = cocktailRepository.filter(alcoholType, category)
        println "Cocktails : $cocktails"
        cocktails*.cocktailName.containsAll(cocktailName)
        where:
        alcoholType           | category                          || cocktailName
        AlcoholType.ALCOHOLIC | CocktailCategory.ORDINARY_DRINK   || ["create sample cocktail"().cocktailName]
        AlcoholType.ALCOHOLIC | CocktailCategory.MILK_FLOAT_SHAKE || []
    }

    def "create sample cocktail"() {
        def cocktailId = CocktailId.create(12442)
        def cocktailName = "Vermouth Cassis"
        def alcoholType = AlcoholType.ALCOHOLIC
        def instructions = "Stir vermouth and creme de cassis in a highball glass with ice cubes. Fill with carbonated water, stir again, and serve."
        def ingredients = [
                new Ingredient("Dry Vermouth", "4.5 cl"),
                new Ingredient("Creme de Cassis", "2.3 cl"),
                new Ingredient("Carbonated water", "")
        ]
        return new Cocktail(cocktailId, cocktailName, alcoholType, CocktailCategory.ORDINARY_DRINK,
                "Highball glass",
                "https://www.thecocktaildb.com/images/media/drink/tswpxx1441554674.jpg",
                instructions,
                ingredients)
    }

}