package com.mastrodaro.earthquakes

import org.koin.core.context.startKoin

object KoinTestInitializer {
    private var initialized = false

    fun initializeKoinContext() {
        if (!initialized) {
            startKoin {
                modules(appModule)
            }
            initialized = true
        }
    }
}
