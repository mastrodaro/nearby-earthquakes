package com.mastrodaro.earthquakes.io

import com.mastrodaro.earthquakes.utils.NearbyEarthquake
import com.mastrodaro.earthquakes.utils.distance
import com.mastrodaro.earthquakes.utils.title
import org.slf4j.LoggerFactory

class ConsolePrinter {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun print(earthquakes: List<NearbyEarthquake>) = earthquakes
        .map { "${it.title} || ${it.distance}" }
        .also {
            it.forEach(::println)
        }
        .also {
            logger.debug("Printed {} earthquakes", earthquakes.size)
        }
}
