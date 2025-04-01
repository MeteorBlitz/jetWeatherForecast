package com.example.jetweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetweatherforecast.screens.main.MainScreen
import com.example.jetweatherforecast.screens.main.MainViewModel
import com.example.jetweatherforecast.screens.search.SearchScreen
import com.example.jetweatherforecast.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation(){
    val naviController = rememberNavController()
    NavHost(navController = naviController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = naviController)
        }
        composable(WeatherScreens.MainScreen.name) {
            val viewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = naviController, viewModel)
        }
        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = naviController)
        }

    }
}