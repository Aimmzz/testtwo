/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example

import org.example.repository.WeatherRepository

fun main() {
    val apiKey = "eccbeb18979a1495da6750f22f0aaea3"
    val city = "Jakarta"
    val repository = WeatherRepository()
    repository.getWeatherForecast(city, apiKey)
}
