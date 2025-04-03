package com.example.jetweatherforecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetweatherforecast.navigation.WeatherNavigation
import com.example.jetweatherforecast.ui.theme.JetWeatherForecastTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherApp()

        }
    }
}

@Composable
fun WeatherApp(){
    JetWeatherForecastTheme {
        WeatherNavigation()
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetWeatherForecastTheme {
        WeatherApp()

    }
}