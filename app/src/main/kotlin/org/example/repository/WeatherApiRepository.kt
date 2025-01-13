package org.example.repository

import org.example.model.WeatherResponse
import org.example.network.WeatherApiService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
                    response.body()?.let {
                        it.list.forEach { weather ->
                            println("Date: ${weather.dtTxt}, Temperature: ${weather.main.temp}°C")
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
