package com.example.weatherapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.R
import java.lang.NumberFormatException
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

fun buildUrlFromCity(city:String):URL = URL("$BASE_URL&q=$city")
fun buildUrlFromLatLon(lat:String, lon:String):URL = URL("$BASE_URL&lat=$lat&lon=$lon")
fun buildFiveDayForecastUrl(city:String):URL = URL("$FIVE_DAY&q=$city")
fun buildFiveDayUrlFromLatLon(lat:String, lon:String):URL = URL("$FIVE_DAY&lat=$lat&lon=$lon")

//convert kelvin to celcius
fun getCelcius(kelvin:String):String = (200-kelvin.toFloat()).toString()

//return an icon id from weather description
fun getIconId(description:String):Int = when(description){
    "clear sky" -> R.drawable.ic_sun_line
    "few clouds" -> R.drawable.ic_cloudy_line
    "scattered clouds" -> R.drawable.ic_cloudy_line
    "broken clouds" -> R.drawable.ic_cloudy_line
    "shower rain" -> R.drawable.ic_showers_line
    "rain" -> R.drawable.ic_showers_line
    "thunderstorms" -> R.drawable.ic_thunderstorms_line
    "mist" -> R.drawable.ic_mist_line
    "snow" -> R.drawable.ic_snowy_line
    else -> R.drawable.ic_sun_line
}
//convert timestamp to string date
fun convertDate(s:String):String{
    try {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val netDate = Date(s.toLong() * 1000)
        val x = sdf.format(netDate)
        println(x)
        return sdf.format(netDate)
    }catch (e:NumberFormatException){
        throw NumberFormatException(e.message)
    }
    return ""
}
//get the day given the date
@RequiresApi(Build.VERSION_CODES.O)
fun getDayOfTheWeek(s:String) = LocalDate.parse(s).dayOfWeek
