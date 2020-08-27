package com.example.weatherapp

import com.example.weatherapp.model.Forecast
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object Request{
    const val GET: String = "GET"
    @Throws(IOException::class)
    fun getData(url:String):String?{
        val urlObj = URL(url)

        val connection = urlObj.openConnection() as HttpURLConnection
        connection.requestMethod = GET

        val responseCode = connection.responseCode

        return if (responseCode == HttpURLConnection.HTTP_OK){
            val `in` = BufferedReader(InputStreamReader(connection.inputStream))
            var inputLine :String?
            val response = StringBuffer()
            while (`in`.readLine().also { inputLine = it }!= null){
                response.append(inputLine)
            }
            println(response.toString())

             val x = Gson()
             val forecast = x.fromJson(response.toString(), Forecast::class.java) as Forecast

            println(forecast.latitude)
            println(forecast.longitude)
            println(forecast.humidity)
            println(forecast.pressure)
            println(forecast.temperature)
            println(forecast.city)

            response.toString()
        }else{
            throw IOException("error in http callback")
        }

    }
}