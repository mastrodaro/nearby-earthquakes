package com.mastrodaro.earthquakes

import com.google.gson.Gson
import com.mastrodaro.earthquakes.io.ConsolePrinter
import com.mastrodaro.earthquakes.io.ConsoleReader
import com.mastrodaro.earthquakes.io.InputReader
import com.mastrodaro.earthquakes.provider.EarthquakesProvider
import com.mastrodaro.earthquakes.provider.HttpClient
import com.mastrodaro.earthquakes.utils.DistanceCalculator
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.dsl.module

val gson = Gson()

val appModule = module {
    single { ConsoleReader(get()) }
    single { ConsolePrinter() }
    single { InputReader() }
    single { EarthquakesProvider(get(), gson) }
    single { HttpClient() }
    single { DistanceCalculator() }
}

class Application : KoinComponent {
    private val reader by inject<ConsoleReader>()
    private val provider by inject<EarthquakesProvider>()
    private val calculator by inject<DistanceCalculator>()
    private val printer by inject<ConsolePrinter>()

    fun run() = runBlocking {
        EarthquakesReporter(reader, provider, calculator, printer).start()
    }
}

fun main() {
    startKoin {
        modules(appModule)
    }
    Application().run()
}
