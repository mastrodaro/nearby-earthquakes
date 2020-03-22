package com.mastrodaro.earthquakes.utils

import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class DistanceCalculatorTest {
    private val calculator = DistanceCalculator()

    @DataProvider
    fun getCoords() = arrayOf(
        arrayOf(53.32055555555556 to -1.7297222222222221, 53.31861111111111 to -1.6997222222222223, 2),
        arrayOf(40.7486 to -73.9864, 40.7486 to -73.8864, 8),
        arrayOf(0 to 0, 0 to 1, 111)
    )

    @Test(dataProvider = "getCoords")
    fun `Distance between given coordinates should be calculated correctly`(
        p1: Coordinates,
        p2: Coordinates,
        result: Int
    ) {
        assertThat(calculator.calculate(p1, p2)).isEqualTo(result)
    }
}
