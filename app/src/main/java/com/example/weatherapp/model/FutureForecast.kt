package com.example.weatherapp.model


import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.util.convertDate
import com.example.weatherapp.util.getDayOfTheWeek
import com.example.weatherapp.util.getIconId
import com.google.gson.annotations.SerializedName
import org.json.JSONArray
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.roundToInt

data class FutureForecast(
    @SerializedName("list")
    val forecasts:Array<HashMap<String, Any>>

    
    ){

@RequiresApi(Build.VERSION_CODES.O)
fun getFutureForecasts(): List<Map<String,Any?>> {
    return forecasts.map { it ->
        val date = it["dt"] as Double
//        it["dt"] = convertDate(date.roundToInt().toString())
        val weather = it.getValue("weather")as List<Map<String, Any>>
        val description = weather.get(0).getValue("description")
        val main = it.getValue("main") as Map<String, Any>
        val wind = it.getValue("wind") as Map<String, Any>
        val icon = getIconId(description.toString())
        val parsedDate = convertDate(date.roundToInt().toString())
        mapOf("dayOfWeek" to getDayOfTheWeek(parsedDate), "date" to parsedDate, "condition" to description, "icon" to icon, "temp" to main["temp"], "pressure" to main["pressure"], "humidity" to main["humidity"], "wind_speed" to wind["speed"], "wind_deg" to wind["deg"])
    }.distinctBy { it.getValue("date") }
    }

}