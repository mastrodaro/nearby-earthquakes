package com.mastrodaro.earthquakes.io

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.Test

private const val TEST_LAT = 120.0
private const val TEST_LON = -30.0

class ConsoleReaderTest {
    private val inputReader = mockk<InputReader> {
        every { read("Latitude: ") } returnsMany listOf(TEST_LAT)
        every { read("Longitude: ") } returnsMany listOf(null, TEST_LON)
    }
    private val reader = ConsoleReader(inputReader)

    @Test
    fun `Console reader should be able to read input values`() {
        assertThat(reader.readCoords()).isEqualTo(TEST_LAT to TEST_LON)
    }
}
