package com.mastrodaro.earthquakes.io

class InputReader {
    fun read(question: String) = run {
        print(question)
        readLine()?.toDoubleOrNull()
    }
}
