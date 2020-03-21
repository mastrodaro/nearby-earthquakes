package com.mastrodaro.earthquakes

import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
}

fun main() {
    startKoin {
        modules(appModule)
    }
}
