package net.hasanguner.handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import org.slf4j.LoggerFactory
import java.io.InputStream
import java.io.OutputStream
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class CoAbstractRequestHandler<in T, out R>(
    private val objectMapper: ObjectMapper = jacksonObjectMapper().disable(FAIL_ON_UNKNOWN_PROPERTIES)
) : RequestStreamHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Suppress("UNCHECKED_CAST")
    private val inputType: Class<T> by lazy {
        (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0].toClass() as Class<T>
    }

    override fun handleRequest(input: InputStream, output: OutputStream, context: Context) = runBlocking {
        objectMapper.readValue(input, inputType)
            .also { log(it) }
            .let { handleAsync(it).await() }
            .also { log(it) }
            .run { objectMapper.writeValue(output, this) }
    }

    abstract suspend fun handle(request: T): R

    private fun handleAsync(request: T): Deferred<R> = async { handle(request) }

    private fun log(data: Any?) = launch {
        data?.also { logger.info("${it::class.simpleName} : $it") }
    }

    private fun Type.toClass(): Class<*> = takeIf { this is Class<*> } as Class<*>?
        ?: (this as ParameterizedType).rawType as Class<*>

}
