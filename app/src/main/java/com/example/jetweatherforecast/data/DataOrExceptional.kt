package com.example.jetweatherforecast.data

class DataOrExceptional<T,Boolean, E:Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E? = null

)
