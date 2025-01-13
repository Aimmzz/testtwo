package org.example.model

data class WeatherResponse(
    val list: List<Weather>
)

data class Weather(
    val dt_txt: String,
    val main: Main
)

data class Main(
    val temp: Float
)
