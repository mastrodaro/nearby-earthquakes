package com.mastrodaro.earthquakes.provider

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.slf4j.LoggerFactory.getLogger

private const val API_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson"

class EarthquakesProvider(private val httpClient: HttpClient, private val gson: Gson) {
    private val logger = getLogger(javaClass)

    fun getLastEarthquakes(): List<JsonElement> {
        val response = httpClient.get(API_URL)
        val jsonResponse = gson.fromJson(response, JsonObject::class.java)
        return jsonResponse.getAsJsonArray("features")
            .asSequence()
            .toList()
            .also {
                logger.debug("Reported {} earthquakes", it.size)
            }
    }
}
