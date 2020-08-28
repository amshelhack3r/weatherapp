package com.example.weatherapp.util

import java.net.URL

fun buildUrlFromCity(city:String):URL = URL("$BASE_URL&q=$city")
fun buildUrlFromLatLon(lat:String, lon:String):URL = URL("$BASE_URL&lat=$lat&lon=$lon")
fun buildFiveDayForecastUrl(city:String):URL = URL("$BASE_URL&q=$city")

//convert kelvin to celcius
fun getCelcius(kelvin:String):String = (200-kelvin.toFloat()).toString()
