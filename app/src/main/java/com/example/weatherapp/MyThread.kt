package com.example.weatherapp

import android.os.Handler
import android.os.Message
import com.example.weatherapp.model.Forecast
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.ContentHandler
import java.net.HttpURLConnection
import java.net.URL

class MyThread(val handler: Handler): Thread() {
    override fun run() {
        super.run()
        getData()
    }

    val GET: String = "GET"
    @Throws(IOException::class)
    fun getData():String?{
        val urlObj = URL(NAIROBI_FORECAST)

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


            println(forecast.city)

            Message.obtain(handler, 1, forecast).apply {
                sendToTarget()
            }
            response.toString()
        }else{
            throw IOException("error in http callback")
        }

    }

}