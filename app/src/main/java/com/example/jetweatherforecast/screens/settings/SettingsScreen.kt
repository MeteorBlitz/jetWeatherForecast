package com.example.jetweatherforecast.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.model.Unit
import com.example.jetweatherforecast.widgets.WeatherAppBar

@Composable
fun SettingsScreen(navController: NavController, settingsViewModel: SettingsViewModel = hiltViewModel()) {
    val unitToggleState = remember { mutableStateOf(false) }
    val measurementunits = listOf("Imperial (F), Metric (C)")
    val choiceFromDb = settingsViewModel.unitList.collectAsState().value
    val defaultChoice = if (choiceFromDb.isEmpty()) measurementunits[0] else choiceFromDb[0].unit
    val choiceState = remember { mutableStateOf(defaultChoice) }
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Settings",
            navController = navController,
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            isMainScreen = false,
            elevation = 5.dp
        ) {
            navController.popBackStack()
        }
    }) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding).fillMaxSize()
        ) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Change Units of Measurement",
                    modifier = Modifier.padding(bottom = 15.dp))

                IconToggleButton(checked = !unitToggleState.value ,onCheckedChange = {
                    unitToggleState.value = !it
                    choiceState.value = if (unitToggleState.value) "Imperial (F)" else "Metric (C)"
                },
                modifier = Modifier.fillMaxWidth(0.5f).padding(5.dp)
                    .clip(shape = RectangleShape)
                    .background(Color.Magenta.copy(alpha = 0.4f))) {

                    Text(if (unitToggleState.value) "Fahrenheit ºF" else "Celsius ºC")
                }

                Button(onClick = {
                    settingsViewModel.deleteAllUnits()
                    settingsViewModel.insertUnit(Unit(unit = choiceState.value))
                },
                    modifier = Modifier.padding(3.dp).align(CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEFBE42)
                    )) {
                    Text(text = "Save",
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium)
                }

            }

        }
    }
}