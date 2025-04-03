package com.example.jetweatherforecast.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.jetweatherforecast.data.DataOrExceptional
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi)  {
    suspend fun getWeather(cityQuery: String, units: MutableState<String>) : DataOrExceptional<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery, units = units.value)
        }catch (e: Exception){
            Log.d("REX", "getWeather: $e")
            return DataOrExceptional(e = e)
        }
        Log.d("INSIDE", "getWeather: $response")
        return DataOrExceptional(data = response)
    }

}