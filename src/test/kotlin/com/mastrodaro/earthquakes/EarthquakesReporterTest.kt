package com.mastrodaro.earthquakes

import com.mastrodaro.earthquakes.io.ConsolePrinter
import com.mastrodaro.earthquakes.io.ConsoleReader
import com.mastrodaro.earthquakes.io.InputReader
import com.mastrodaro.earthquakes.provider.EarthquakesProvider
import com.mastrodaro.earthquakes.provider.HttpClient
import com.mastrodaro.earthquakes.utils.DistanceCalculator
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.Test

class EarthquakesReporterTest {
    private val inputReader = mockk<InputReader> {
        every { read(any()) } returnsMany listOf(0.0)
    }
    private val reader = ConsoleReader(inputReader)
    private val client = mockk<HttpClient>()
    private val provider = EarthquakesProvider(client, gson)
    private val calculator = DistanceCalculator()
    private val printer = ConsolePrinter()
    private val expectedEarthquakes = listOf(
        "M 3.7 - 0km ESE of Indios, Puerto Rico || 7562",
        "M 1.2 - 16km S of Searles Valley, CA || 12452",
        "M 2.0 - 84km ENE of Cantwell, Alaska || 12453",
        "M 1.2 - 15km SSW of Searles Valley, CA || 12454",
        "M 0.8 - 12km WSW of Searles Valley, CA || 12457",
        "M 0.9 - 13km W of Searles Valley, CA || 12458",
        "M 1.0 - 22km N of Ridgecrest, CA || 12466",
        "M 1.1 - 23km N of Ridgecrest, CA || 12467",
        "M 1.2 - 20km ESE of Little Lake, CA || 12468",
        "M 1.7 - 13km SSW of Olancha, CA || 12488"
    )

    init {
        every { client.get(any()) } returns javaClass.getResource("/exampleApiResponse.json").readText()
    }

    @Test
    fun `Application should be able to receive coords and print list of earthquakes`() {
        runBlocking {
            val reportResult = EarthquakesReporter(reader, provider, calculator, printer).start()
            assertThat(reportResult).containsAll(expectedEarthquakes)
        }
    }
}
