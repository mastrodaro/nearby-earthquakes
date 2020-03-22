package com.mastrodaro.earthquakes.utils

import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

private const val EARTH_RADIUS = 6371 // in kilometers

class DistanceCalculator {
    fun calculate(point1: Coordinates, point2: Coordinates): Int {
        val p1LatRad = point1.lat.toRadians()
        val p2LatRad = point2.lat.toRadians()
        val p1LonRad = point1.lon.toRadians()
        val p2LonRad = point2.lon.toRadians()

        val delLatRad = (p2LatRad - p1LatRad)
        val delLonRad = (p2LonRad - p1LonRad)

        val a = sin(delLatRad / 2).pow(2) + cos(p1LatRad) * cos(p2LatRad) * sin(delLonRad / 2).pow(2)
        val c = 2 * asin(sqrt(a))

        return (EARTH_RADIUS * c).roundToInt()
    }
}
