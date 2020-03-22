package com.mastrodaro.earthquakes.provider

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.mastrodaro.earthquakes.KoinTestInitializer
import org.assertj.core.api.Assertions.assertThat
import org.koin.test.KoinTest
import org.koin.test.inject
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

class EarthquakesProviderTest : KoinTest {
    private val provider by inject<EarthquakesProvider>()

    @BeforeClass
    fun init() = KoinTestInitializer.initializeKoinContext()

    @Test
    fun `Earthquakes provider should be able to get last earthquakes`() {
        val lastEarthquakes = provider.getLastEarthquakes()
        assertThat(lastEarthquakes).hasSizeGreaterThan(0)
    }

    @Test
    fun `Fetched earthquakes should contain required data`() {
        val earthquake = provider.getLastEarthquakes().first() as JsonObject
        assertThat(earthquake.get("properties")).isNotNull
        assertThat(earthquake.get("geometry")).isNotNull

        val properties = earthquake.get("properties") as JsonObject
        assertThat(properties.get("title")).isNotNull

        val geometry = earthquake.get("geometry") as JsonObject
        assertThat(geometry.get("coordinates")).isInstanceOf(JsonArray::class.java)
    }
}
