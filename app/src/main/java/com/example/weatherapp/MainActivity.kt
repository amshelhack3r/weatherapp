package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.example.weatherapp.model.Forecast
import kotlinx.android.synthetic.main.fragment_city.*

class MainActivity : AppCompatActivity() {

    val handler = object:Handler(Looper.getMainLooper()){
        override fun handleMessage(msg:Message){
            val forecast = msg.obj as Forecast
            println(forecast.city)
            cv_city_name.text = forecast.city
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_city)

        MyThread(handler).start()
    }


}