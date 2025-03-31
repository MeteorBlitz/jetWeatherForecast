package com.example.jetweatherforecast.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.data.DataOrExceptional
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.widgets.WeatherAppBar

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()){
    val weatherData = produceState<DataOrExceptional<Weather, Boolean, Exception>>(
        initialValue = DataOrExceptional(loading = true)
    ) {
        // value = mainViewModel.data.value
        value = mainViewModel.getWeather(city = "Portland")
    }.value

    if (weatherData.loading == true){
        CircularProgressIndicator()
    }else if (weatherData.data != null){
        //Text(text ="MainScreen: ${weatherData.data!!.city.country}" )
        MainScaffold(weather = weatherData.data!!, navController = navController)
    }



}
@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(title = weather.city.name + ", ${weather.city.country}")
    }) { innerPadding ->
        Column( modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            MainContent(data = weather)
        }
    }

}

@Composable
fun MainContent(data: Weather) {
    Text(text = data.city.country)
}
