package com.mastrodaro.earthquakes.provider

import com.mastrodaro.earthquakes.utils.toUri
import org.slf4j.LoggerFactory.getLogger
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers.ofString

class HttpClient {
    private val logger = getLogger(javaClass)

    private val headers = mutableMapOf(
        "Accept" to "application/json"
    )
    private val client = HttpClient.newHttpClient()

    fun get(address: String): String? {
        val request = buildRequest(address)
        return client.send(request, ofString())
            .body()
            .also {
                logger.debug("Received string of size {}", it.length)
            }
    }

    private fun buildRequest(address: String) = HttpRequest.newBuilder()
        .uri(address.toUri())
        .headers(*headers.flatMap { listOf(it.key, it.value) }.toTypedArray())
        .GET()
        .build()
        .also {
            logger.debug("Invoke GET {}", address)
        }
}
