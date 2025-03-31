package com.example.jetweatherforecast.repository

import com.example.jetweatherforecast.data.DataOrExceptional
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi)  {
    suspend fun getWeather(cityQuery: String) : DataOrExceptional<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery)
        }catch (e: Exception){
            return DataOrExceptional(e = e)
        }
        return DataOrExceptional(data = response)
    }

}