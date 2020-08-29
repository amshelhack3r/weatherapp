package com.example.weatherapp.fragment

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.adapter.CityItemRecyclerViewAdapter
import com.example.weatherapp.adapter.FutureForecastRecyclerViewAdapter
import com.example.weatherapp.model.Forecast
import com.example.weatherapp.model.FutureForecast
import com.example.weatherapp.util.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.mockup.*

private const val ARG_PARAM1 = "city"
private const val LATITUDE = "latitude"
private const val LONGITUDE = "longitude"

class CityFragment : Fragment(), View.OnClickListener {
    lateinit var forecast:Forecast
    //lazy initialization
    private val preferences: SharedPreferences by lazy {
        activity!!.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }
    private val handler = object: Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message){
            progressBar.visibility = View.INVISIBLE

            if(msg.what == 1) {
                val gson = Gson()
                forecast = gson.fromJson(msg.obj as String, Forecast::class.java) as Forecast

                cv_city_name.text = forecast.city
                cv_humidity.text = "${forecast.humidity} %"
                cv_pressure.text = "${forecast.pressure} hpa"
                cv_temperature.text = forecast.temperature
                cv_wind.text = "${forecast.windDeg} deg"
                cv_wind2.text = "${forecast.windSpeed} m/s"
                cv_weather_condition.text = forecast.weatherCondition
                var resID: Int = getIconId(forecast.weatherCondition)
                cv_mainIcon.setImageResource(resID)

                //check shared preferences if it contains that bookmark.
                //if it doesnt then we activate our bookmark button to save a city
                var savedBookmarks = preferences.getStringSet("bookmarks", mutableSetOf());
                if (!savedBookmarks!!.contains(forecast.city)) {
                    bookmarkBtn.isEnabled = true
                }
            }else{
                Toast.makeText(context, "Try another name", Toast.LENGTH_LONG).apply {
                    show()
                }

                Thread.sleep(1000)
                activity!!.supportFragmentManager.popBackStack()
            }
        }
    }

    private val futureHandler =object :Handler(Looper.getMainLooper()){
        @RequiresApi(Build.VERSION_CODES.O)
        override fun handleMessage(msg: Message) {
            progressBar.visibility = View.INVISIBLE
            val gson = Gson()

            val future_forecast = gson.fromJson<String>(msg.obj as String, FutureForecast::class.java) as FutureForecast

            val layoutManager = LinearLayoutManager(context)
            futureforecast.layoutManager=layoutManager
            futureforecast.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            futureforecast.isNestedScrollingEnabled = false
            val values = future_forecast.getFutureForecasts()
            futureforecast.adapter =
                FutureForecastRecyclerViewAdapter(
                    values
                )




        }
    }
    // TODO: Rename and change types of parameters
    private var city: String? = null
    private var latitude: String? = null
    private var longitude: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            city = it.getString(ARG_PARAM1)
            latitude = it.getString(LATITUDE)
            longitude = it.getString(LONGITUDE)
        }
        val url = if(city!=null) buildUrlFromCity(city!!) else buildUrlFromLatLon(latitude!!, longitude!!)
        val future_url = if(city!=null) buildFiveDayForecastUrl(city!!) else buildFiveDayUrlFromLatLon(latitude!!, longitude!!)

        BackGroundFetch(handler, url).start()
        BackGroundFetch(futureHandler, future_url).start()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookmarkBtn.isEnabled =false
        activity!!.findViewById<FloatingActionButton>(R.id.bookmarkBtn).setOnClickListener { view ->
            var bookmarks = preferences.getStringSet("bookmarks", mutableSetOf())
            println(forecast.city)
            val committed =  preferences.edit().apply{
                bookmarks?.add(forecast.city)
                clear()
                putStringSet("bookmarks", bookmarks)
                commit()
            }
            Toast.makeText(context, "BOOKMARK ADDED", Toast.LENGTH_LONG).show()
            bookmarkBtn.isEnabled = false

        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mockup, container, false)
    }
    override fun onClick(p0: View?) {

        val fragment = CityListFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment)
//        transaction.addToBackStack(null)
        transaction.commit()
    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            CityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }

        @JvmStatic
        fun newInstanceFromLatLong(lat: String, lon:String) =
            CityFragment().apply {
                arguments = Bundle().apply {
                    putString(LATITUDE, lat)
                    putString(LONGITUDE, lon)
                }
            }
    }


}