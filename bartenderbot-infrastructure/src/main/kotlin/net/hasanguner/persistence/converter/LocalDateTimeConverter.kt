package net.hasanguner.persistence.converter

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import java.time.LocalDateTime

class LocalDateTimeConverter : DynamoDBTypeConverter<String, LocalDateTime> {

    override fun unconvert(dateTimeString: String): LocalDateTime = LocalDateTime.parse(dateTimeString)

    override fun convert(dateTime: LocalDateTime): String = dateTime.toString()
}