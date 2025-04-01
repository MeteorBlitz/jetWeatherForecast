package com.example.jetweatherforecast.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.model.WeatherItem
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDateTime
import com.example.jetweatherforecast.utils.formatDecimals

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
