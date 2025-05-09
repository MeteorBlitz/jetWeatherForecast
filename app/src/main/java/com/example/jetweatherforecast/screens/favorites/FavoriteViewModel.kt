package com.example.jetweatherforecast.screens.favorites

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweatherforecast.model.Favorite
import com.example.jetweatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDbRepository) : ViewModel() {
    private  val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged()
                .collect {listOfFavs ->
                    if (listOfFavs.isEmpty()){
                        //_favList.value = emptyList()
                        Log.d("TAG", "fav_list: Empty favs}")
                    }else{
                        _favList.value = listOfFavs
                        Log.d("TAG", "fav_list: ${_favList.value}")
                    }

                }

        }
    }

    fun insertFavorite(favorite: Favorite)
    = viewModelScope.launch { repository.insertFavorite(favorite) }
    fun updateFavorite(favorite: Favorite)
            = viewModelScope.launch { repository.updateFavorite(favorite) }
    fun deleteFavorite(favorite: Favorite)
            = viewModelScope.launch { repository.deleteFavorite(favorite) }
}