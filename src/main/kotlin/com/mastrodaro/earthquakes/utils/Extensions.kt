package com.mastrodaro.earthquakes.utils

import java.net.URI

typealias Coordinates = Pair<Double, Double>
typealias NearbyEarthquake = Pair<Int, String>

val Coordinates.lat: Double
    get() = first

val Coordinates.lon: Double
    get() = second

val NearbyEarthquake.title: String
    get() = second

val NearbyEarthquake.distance: Int
    get() = first

fun Double.toRadians() = Math.toRadians(this)

fun String.toUri() = URI(this)
