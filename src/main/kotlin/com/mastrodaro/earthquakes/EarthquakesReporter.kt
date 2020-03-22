package com.mastrodaro.earthquakes

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mastrodaro.earthquakes.io.ConsolePrinter
import com.mastrodaro.earthquakes.io.ConsoleReader
import com.mastrodaro.earthquakes.provider.EarthquakesProvider
import com.mastrodaro.earthquakes.utils.Coordinates
import com.mastrodaro.earthquakes.utils.DistanceCalculator
import com.mastrodaro.earthquakes.utils.NearbyEarthquake
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.slf4j.LoggerFactory

const val PRINT_LIMIT = 10

class EarthquakesReporter(
    private val reader: ConsoleReader,
    private val provider: EarthquakesProvider,
    private val calculator: DistanceCalculator,
    private val printer: ConsolePrinter
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun start(): List<String> {
        val lastEarthquakes = GlobalScope.async {
            provider.getLastEarthquakes()
        }
        val coords = reader.readCoords()
        val nearbyEarthquakes = run {
            if (!lastEarthquakes.isCompleted) {
                println("Processing... please wait.")
            }
            getNearbyEarthquakes(lastEarthquakes.await(), coords)
        }
        return printer.print(nearbyEarthquakes)
    }

    private fun getNearbyEarthquakes(earthquakes: List<JsonElement>, coords: Coordinates): List<NearbyEarthquake> =
        earthquakes.map {
                it as JsonObject
                val (lat, lon) = it["geometry"]
                    .asJsonObject["coordinates"]
                    .asJsonArray
                    .asSequence()
                    .toList()
                calculator.calculate(
                    coords,
                    lat.asDouble to lon.asDouble
                ) to it["properties"].asJsonObject["title"].asString
            }.sortedBy { (distance, _) ->
                distance
            }.distinctBy { (distance, _) ->
                distance
            }.subList(0, PRINT_LIMIT)
            .also {
                logger.debug("Sorted list of {} earthquakes and truncated to {}", earthquakes.size, PRINT_LIMIT)
            }
}
