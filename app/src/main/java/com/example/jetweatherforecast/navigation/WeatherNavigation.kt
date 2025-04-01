package com.example.jetweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
        //www.google.com/cityname="seattle"
        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}",
            arguments = listOf(
                navArgument(name = "city"){
                    type = NavType.StringType
                })
            ){ navBack ->
            navBack.arguments?.getString("city").let { city ->
                val viewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = naviController, viewModel, city = city)
            }
        }
        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = naviController)
        }

    }
}