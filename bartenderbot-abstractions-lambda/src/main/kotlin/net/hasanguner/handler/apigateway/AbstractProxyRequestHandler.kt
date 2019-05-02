package net.hasanguner.handler.apigateway

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.experimental.launch
import org.slf4j.LoggerFactory
import java.io.InputStream
import java.io.OutputStream
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class AbstractProxyRequestHandler<in T, out R>(
    private val objectMapper: ObjectMapper = jacksonObjectMapper().disable(FAIL_ON_UNKNOWN_PROPERTIES)
) : RequestStreamHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Suppress("UNCHECKED_CAST")
    private val inputType: Class<T> by lazy {
        (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0].toClass() as Class<T>
    }

    override fun handleRequest(input: InputStream, output: OutputStream, context: Context) = input
        .let { objectMapper.readValue(it, APIGatewayProxyRequestEvent::class.java) }
        .also { log(it) }
        .body
        .let { objectMapper.readValue(it, inputType) }
        .let { handle(it) }
        .let { objectMapper.writeValueAsString(it) }
        .let { APIGatewayProxyResponseEvent().apply { statusCode = 200;body = it } }
        .also { log(it) }
        .run { objectMapper.writeValue(output, this) }

    abstract fun handle(request: T): R


    private fun log(data: Any?) = launch {
        data?.also { logger.info("${it::class.simpleName} : $it") }
    }

    private fun Type.toClass(): Class<*> = takeIf { this is Class<*> } as Class<*>?
        ?: (this as ParameterizedType).rawType as Class<*>

}
