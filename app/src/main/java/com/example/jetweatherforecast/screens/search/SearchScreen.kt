package com.example.jetweatherforecast.screens.search

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetweatherforecast.navigation.WeatherScreens
import com.example.jetweatherforecast.widgets.WeatherAppBar

@Composable
fun SearchScreen(navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Search",
            navController = navController,
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            isMainScreen = false,
            elevation = 5.dp
        ){
            navController.popBackStack()
        }
    }) { innerPadding ->
        Surface(modifier = Modifier
            .padding(innerPadding)) {
           Column(verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally){
               SearchBar(modifier = Modifier
                   .fillMaxWidth()
                   .padding(16.dp)
                   .align(Alignment.CenterHorizontally)){mCity ->
                   navController.navigate(WeatherScreens.MainScreen.name+"/$mCity")
                   Log.d("TAG", "SearchScreen: $mCity")
               }

           }
        }
    }
}

@Composable
fun SearchBar(modifier: Modifier= Modifier,onSearch : (String) -> Unit = {}) {
    val searchQueryState = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotEmpty()
    }
    Column {
        CommonTextField(
            valueState = searchQueryState,
            placeholder = "Seattle",
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions // empty text return nothing
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            }
        )
    }
}

@Composable
fun CommonTextField(valueState: MutableState<String>,
                    placeholder: String,
                    keyboardType: KeyboardType = KeyboardType.Text,
                    imeAction: ImeAction = ImeAction.Next,
                    onAction: KeyboardActions = KeyboardActions.Default ) {

    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = {Text(placeholder)},
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        singleLine = true,
        maxLines = 1,
        keyboardActions = onAction,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Blue,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth()
    )

}