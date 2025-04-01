package com.example.jetweatherforecast.screens.main

import android.R.attr.contentDescription
import android.media.Image
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDateTime
import com.example.jetweatherforecast.utils.formatDecimals
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

@Composable
fun WeatherDetailRow(weatherItem: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"
    Surface(modifier = Modifier.padding(3.dp).fillMaxWidth(),
        shape = CircleShape.copy(
            topEnd = CornerSize(6.dp)
        ),
        color = Color.White) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = formatDate(weatherItem.dt) // Tue, Apr 1
                    .split(",")[0],
                    modifier = Modifier.padding(start = 5.dp))
            WeatherStateImage(imageUrl = imageUrl)
            Surface(modifier = Modifier.padding(1.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)) {
                Text(text = weatherItem.weather[0].description,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(4.dp))
            }
            Text(text = buildAnnotatedString {
                withStyle(style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Blue,
                    fontWeight = FontWeight.SemiBold
                ).toSpanStyle()){
                    append(formatDecimals(weatherItem.temp.max) + "°")
                }
                withStyle(style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.LightGray,
                ).toSpanStyle()){
                    append(formatDecimals(weatherItem.temp.min) + "°")
                }

            })

        }
    }


}

@Composable
fun SunsetSunriseRow(weatherItem: WeatherItem) {
    Row(modifier = Modifier.fillMaxWidth()
        .padding(top = 15.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(R.drawable.sunrise),
                contentDescription = "sunrise icon",
                modifier = Modifier.size(30.dp))
            Text(text = formatDateTime(weatherItem.sunrise),
                style = MaterialTheme.typography.titleMedium)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(R.drawable.sunset),
                contentDescription = "sunset icon",
                modifier = Modifier.size(30.dp))
            Text(text = formatDateTime(weatherItem.sunset),
                style = MaterialTheme.typography.titleMedium)
        }
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
