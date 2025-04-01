package com.example.jetweatherforecast.screens.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.data.DataOrExceptional
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.navigation.WeatherScreens
import com.example.jetweatherforecast.widgets.HumidityWindPressureRow
import com.example.jetweatherforecast.widgets.SunsetSunriseRow
import com.example.jetweatherforecast.widgets.WeatherAppBar
import com.example.jetweatherforecast.widgets.WeatherDetailRow
import com.example.jetweatherforecast.widgets.WeatherStateImage

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String?
){
    Log.d("TAG", "MainScreen: $city")
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
        WeatherAppBar(title = weather.city.name + ", ${weather.city.country}",
            //icon = Icons.AutoMirrored.Filled.ArrowBack,
            navController = navController,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
            elevation = 5.dp){
            Log.d("TAG", "MainScaffold: Button Clicked")
        }
    }) { innerPadding ->
        Column( modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            MainContent(data = weather)
        }
    }

}

@Composable
fun MainContent(data: Weather) {

    val imageUrl = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"
    Column (modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){

        Text(text = "Nov 29", style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(6.dp),
            fontWeight = FontWeight.SemiBold,)

        Surface(modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)) {
            Column (verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){

                WeatherStateImage(imageUrl = imageUrl)
                Text(text = "56",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,)
                Text(text = "Snow",
                    style = MaterialTheme.typography.titleMedium,
                    fontStyle = FontStyle.Italic,)

            }
        }
        HumidityWindPressureRow(weatherItem = data.list[0])
        HorizontalDivider()
        SunsetSunriseRow(weatherItem = data.list[0])
        Text(text = "This Week",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 5.dp))

        Surface(modifier = Modifier.fillMaxSize(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(size = 14.dp)) {

            LazyColumn(modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)) {
                items(items = data.list) {item ->
                    WeatherDetailRow(weatherItem = item)

                }
            }

        }
    }
}

