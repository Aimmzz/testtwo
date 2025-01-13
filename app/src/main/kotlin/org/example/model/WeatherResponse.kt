package org.example.model

data class WeatherResponse(
    val list: List<Weather>
)

data class Weather(
    val dtTxt: String,
    val main: Main
)

data class Main(
    val temp: Float
)
