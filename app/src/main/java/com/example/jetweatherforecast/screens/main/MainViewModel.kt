package com.example.jetweatherforecast.screens.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetweatherforecast.data.DataOrExceptional
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var repository: WeatherRepository) : ViewModel() {

    val data : MutableState<DataOrExceptional<Weather,Boolean,Exception>>
    = mutableStateOf(DataOrExceptional(null,true,Exception("")))

    suspend fun getWeather(city: String, units: MutableState<String>)
    : DataOrExceptional<Weather,Boolean,Exception>{
        return  repository.getWeather(cityQuery = city, units = units)
    }
/*
    init {
        loadWeather()
    }

    private fun loadWeather() {
        getWeather("Seattle")
    }

    private fun getWeather(city: String) {
        viewModelScope.launch {
            if (city.isEmpty()) return@launch
            data.value.loading = true
            data.value = repository.getWeather(cityQuery = city)
            if (data.value.data.toString().isNotEmpty()) data.value.loading = false
        }
        Log.d("GET", "getWeather: ${data.value.data.toString()}")
    }

 */
}