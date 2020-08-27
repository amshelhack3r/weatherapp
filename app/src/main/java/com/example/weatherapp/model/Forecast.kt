package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("coord")
    val coord:HashMap<String, String>,
    @SerializedName("weather")
    val basic:Array<HashMap<String, String>>,
    @SerializedName("main")
    val main:HashMap<String, String>,
    @SerializedName("wind")
    val wind:HashMap<String, String>,
    @SerializedName("name")
    val city:String
){
    val latitude:String
        get()=this.coord.get("lat") ?: ""

    val longitude:String
        get()=this.coord.get("lon") ?: ""

    val temperature:String
        get()=this.main.get("temp") ?: ""

    val pressure:String
        get()=this.main.get("pressure") ?: ""

    val humidity:String
        get()=this.main.get("humidity") ?: ""

    val windSpeed:String
        get()=this.wind.get("speed") ?: ""

    val windDeg:String
        get()=this.wind.get("deg") ?: ""



}