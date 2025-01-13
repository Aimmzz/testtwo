package org.example.repository

import org.example.model.WeatherResponse
import org.example.network.WeatherApiService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class WeatherRepository {
    private val api: WeatherApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(WeatherApiService::class.java)
    }

    fun getWeatherForecast(city: String, apiKey: String) {
        val call = api.getWeatherForecast(city, apiKey)
        call.enqueue(object : retrofit2.Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: retrofit2.Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { weatherResponse ->
                        println("Weather Forecast:")

                        val dailyTemperatures = mutableMapOf<String, Double>()
                        val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.ENGLISH)
                        val dateFormatForKey = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

                        val currentDate = Date()
                        val currentDateString = dateFormatForKey.format(currentDate)

                        weatherResponse.list.forEach { weather ->
                            val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(weather.dt_txt)
                            val dateKey = dateFormatForKey.format(date)

                            if (dateKey != currentDateString && !dailyTemperatures.containsKey(dateKey)) {
                                val tempInCelsius = weather.main.temp - 273.15
                                dailyTemperatures[dateKey] = tempInCelsius
                            }
                        }

                        dailyTemperatures.entries.take(5).forEach { (dateKey, temp) ->
                            val date = dateFormatForKey.parse(dateKey)
                            val formattedDate = dateFormat.format(date)
                            println("$formattedDate: ${"%.2f".format(temp)} C")
                        }
                    }
                } else {
                    println("Failed to retrieve weather data.")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }
}