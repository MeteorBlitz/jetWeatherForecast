![JetWeatherForecast](https://github.com/MeteorBlitz/jetWeatherForecast/blob/main/screenshots/home_screen.png?raw=true)
# JetWeatherForecast 🌦️

JetWeatherForecast is a modern weather forecasting app built with Kotlin and Jetpack Compose.  
It fetches real-time weather data using the OpenWeatherMap API, displays current and 7-day forecast details, and offers a clean, responsive UI.

## 🔥 Features

- 🌍 Fetch real-time weather data using **OpenWeatherMap API**.
- 🗺️ Search for any city to view detailed weather information.
- 💾 Save your favorite cities locally using **Room Database**.
- 🔥 Switch between **Celsius and Fahrenheit** in the **Settings Screen**.
- 🏡 Home Screen:
  - Top half: Circular UI displaying temperature, sunset, sunrise, wind speed, and humidity.
  - Bottom half: 7-day forecast showing weather trends for the week.
- 🛡️ Implemented **Hilt + Dagger** for clean and scalable dependency injection.
- 🧠 Followed **MVVM architecture** and **Repository pattern**.
- 🎨 Fully designed using **Jetpack Compose** with modern UI/UX practices.

## 🛠️ Built With

- Kotlin
- Jetpack Compose
- Hilt / Dagger
- Room Database
- Retrofit
- OpenWeatherMap API
- MVVM Architecture

## 📸 Screenshots

<table>
  <tr>
    <td><img src="screenshots/splash_screen.png" width="180"/></td>
    <td><img src="screenshots/home_screen.png" width="180"/></td>
    <td><img src="screenshots/favorites_list_screen.png" width="180"/></td>
    <td><img src="screenshots/Settings_Screen.png" width="180"/></td>
    <td><img src="screenshots/menu_screen.png" width="180"/></td>
  </tr>
  <tr>
    <td><center>Splash</center></td>
    <td><center>Home</center></td>
    <td><center>Favorites</center></td>
    <td><center>Settings</center></td>
    <td><center>Menu</center></td>
  </tr>
</table>
## 📂 Installation

1. Clone the repository
2. Open in Android Studio
3. Add your OpenWeatherMap API Key to `constants.kt`
4. Run the app on your device or emulator
