package com.mastrodaro.earthquakes.io

import com.mastrodaro.earthquakes.KoinTestInitializer
import org.assertj.core.api.Assertions.assertThat
import org.koin.test.KoinTest
import org.koin.test.inject
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

class ConsolePrinterTest : KoinTest {
    private val printer by inject<ConsolePrinter>()

    @BeforeClass
    fun init() = KoinTestInitializer.initializeKoinContext()

    @Test
    fun `Collected earthquakes should be printed in correct format`() {
        val earthquakesData = listOf(
            2 to "title 2",
            1 to "title 1",
            3 to "title 3"
        )
        val expectedFormat = listOf(
            "title 2 || 2",
            "title 1 || 1",
            "title 3 || 3"
        )
        assertThat(printer.print(earthquakesData)).containsAll(expectedFormat)
    }
}
