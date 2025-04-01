package com.example.jetweatherforecast.screens.main

import android.R.attr.contentDescription
import android.media.Image
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter.State
import coil3.compose.rememberAsyncImagePainter
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.data.DataOrExceptional
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.model.WeatherItem
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
        WeatherAppBar(title = weather.city.name + ", ${weather.city.country}",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            navController = navController,
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
    Column (modifier = Modifier.padding(4.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){

        Text(text = "Nov 29", style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(6.dp),
            fontWeight = FontWeight.SemiBold,)

        Surface(modifier = Modifier.padding(4.dp).size(200.dp),
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
    }
}

@Composable
fun HumidityWindPressureRow(weatherItem: WeatherItem) {
    Row(modifier = Modifier.padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp))
            Text(text = "${weatherItem.humidity}%",
                style = MaterialTheme.typography.titleMedium)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(20.dp))
            Text(text = "${weatherItem.humidity} psi",
                style = MaterialTheme.typography.titleMedium)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(R.drawable.wind),
                contentDescription = "wind icon",
                modifier = Modifier.size(20.dp))
            Text(text = "${weatherItem.humidity} mph",
                style = MaterialTheme.typography.titleMedium)
        }
    }

}

@Composable
fun WeatherStateImage(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Weather Icon",
        modifier = Modifier.size(80.dp)
    )
}
