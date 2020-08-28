package com.example.weatherapp.util

import android.os.Handler
import android.os.Message
import com.example.weatherapp.model.Forecast
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class BackGroundFetch(private val handler: Handler, private val url:URL): Thread() {
    override fun run() {
        super.run()
        println(url)
        getData()

    }

    val GET: String = "GET"
    @Throws(IOException::class)
    fun getData(): Message? {


        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = GET

        val responseCode = connection.responseCode

        return if (responseCode == HttpURLConnection.HTTP_OK){
            val `in` = BufferedReader(InputStreamReader(connection.inputStream))
            var inputLine :String?
            val response = StringBuffer()
            while (`in`.readLine().also { inputLine = it }!= null){
                response.append(inputLine)
            }

            val gson = Gson()
            val forecast = gson.fromJson(response.toString(), Forecast::class.java) as Forecast



            Message.obtain(handler, 1, forecast).apply {
                sendToTarget()
            }

        }else{
            Message.obtain(handler, 2).apply {
                sendToTarget()
            }
        }

    }

}