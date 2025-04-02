package com.example.jetweatherforecast.widgets

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
) {
    val showDialog = remember {
        mutableStateOf(false)
    }
    if (showDialog.value) {
        SettingsDropDownMenu(showDialog = showDialog, navController = navController)
    }
    TopAppBar(title = {Text(title,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp)},
        actions = {
            if (isMainScreen){
                IconButton(onClick = { onAddActionClicked.invoke() }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
                }
                IconButton(onClick = { showDialog.value = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "more icon")

                }

            }else Box {}
        },
        navigationIcon = {
            if (icon != null){
                IconButton(onClick = { onButtonClicked.invoke() }) {
                    Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.shadow(elevation=elevation))

}

@Composable
fun SettingsDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded = remember {
        mutableStateOf(true)
    }
    val items = listOf("About", "Favorites", "Settings")
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopEnd)
        .absolutePadding(top = 45.dp, right = 20.dp)) {
        DropdownMenu(expanded = expanded.value,
            onDismissRequest = { expanded.value = false},
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(
                    onClick = {
                        expanded.value = false
                        showDialog.value = false
                        when(text){
                            "About" -> Log.d("TAG", "SettingsDropDownMenu: About menu clicked")
                            "Favorites" -> Log.d("TAG", "SettingsDropDownMenu: Favorites menu clicked")
                            "Settings" -> Log.d("TAG", "SettingsDropDownMenu: Settings menu clicked")
                        }
                    },
                    leadingIcon = {
                        val icon = when (text) {
                            "About" -> Icons.Filled.Info
                            "Favorites" -> Icons.Filled.Favorite
                            else -> Icons.Default.Info
                        }
                        Icon(
                            imageVector = icon,
                            contentDescription = text,
                            tint = Color.LightGray
                        )
                      },
                    text = {Text(text = text)},
                )
            }

        }

    }
}