package com.mastrodaro.earthquakes.io

import com.mastrodaro.earthquakes.utils.Coordinates
import org.slf4j.LoggerFactory.getLogger

class ConsoleReader(private val inputReader: InputReader) {
    private val logger = getLogger(javaClass)

    fun readCoords(): Coordinates {
        println("Please provide city coordinates in separated lines, latitude then longitude:")
        val lat = readValue("Latitude: ")
        val lon = readValue("Longitude: ")
        logger.debug("Read coordinates (lat = {}, lon = {})", lat, lon)
        return lat to lon
    }

    private fun readValue(question: String): Double {
        loop@ while (true) {
            when (val value = inputReader.read(question)) {
                null -> {
                    println("Entered value is invalid - try again")
                    continue@loop
                }
                else -> return value
            }
        }
    }
}
