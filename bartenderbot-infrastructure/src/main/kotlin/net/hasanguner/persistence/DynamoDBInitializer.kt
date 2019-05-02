package net.hasanguner.persistence

import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper

class DynamoDBInitializer {

    fun getDynamoDBClient(properties: DynamoDBProperties): AmazonDynamoDB =
        AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.fromName(properties.region))
            .build()

    fun getDynamoDBMapper(properties: DynamoDBProperties): DynamoDBMapper =
        getDynamoDBClient(properties)
            .let { DynamoDBMapper(it) }

}
