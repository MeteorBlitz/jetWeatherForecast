package com.example.jetweatherforecast.repository

import android.util.Log
import com.example.jetweatherforecast.data.DataOrExceptional
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi)  {
    suspend fun getWeather(cityQuery: String) : DataOrExceptional<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery)
        }catch (e: Exception){
            Log.d("REX", "getWeather: $e")
            return DataOrExceptional(e = e)
        }
        Log.d("INSIDE", "getWeather: $response")
        return DataOrExceptional(data = response)
    }

}