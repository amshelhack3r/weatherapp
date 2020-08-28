package com.example.weatherapp.fragment

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.example.weatherapp.R
import com.example.weatherapp.model.Forecast
import com.example.weatherapp.util.*
import kotlinx.android.synthetic.main.fragment_city.*

private const val ARG_PARAM1 = "city"
private const val LATITUDE = "latitude"
private const val LONGITUDE = "longitude"

class CityFragment : Fragment(), View.OnClickListener {
    lateinit var forecast:Forecast
    var progress:ProgressBar = ProgressBar(requireContext())
    //lazy initialization
    private val preferences: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }
    private val handler = object: Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message){

            if(msg.what == 1) {


                forecast = msg.obj as Forecast
                cv_city_name.text = forecast.city
                cv_humidity.text = "${forecast.humidity} %"
                cv_pressure.text = "${forecast.pressure} hpa"
                cv_temperature.text = forecast.temperature
                cv_wind.text = "${forecast.windDeg} deg"
                cv_wind2.text = "${forecast.windSpeed} m/s"
                cv_weather_condition.text = forecast.weatherCondition
                var resID: Int = when (forecast.weatherCondition) {
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

                cv_mainIcon.setImageResource(resID)

                //check shared preferences if it contains that bookmark.
                //if it doesnt then we activate our bookmark button to save a city
                var savedBookmarks = preferences.getStringSet("bookmarks", mutableSetOf());
                if (!savedBookmarks!!.contains(forecast.city)) {
                    bookmarkBtn.isEnabled = true
                }
            }else{
                Toast.makeText(requireContext(), "Try another name", Toast.LENGTH_LONG).apply {
                    show()
                }

                Thread.sleep(1000)
                requireActivity().supportFragmentManager.popBackStack()
            }
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
        BackGroundFetch(handler, url).start()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bookmarkBtn.isEnabled =false
        bookmarkBtn.setOnClickListener(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_city, container, false)
    }
    override fun onClick(p0: View?) {
        var bookmarks = preferences.getStringSet("bookmarks", mutableSetOf())
        println(forecast.city)
        preferences.edit().apply{
            bookmarks?.add(forecast.city)
            putStringSet("bookmarks", bookmarks)
            apply()
        }

        bookmarkBtn.isEnabled = false
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment cityFragment.
         */
        // TODO: Rename and change types and number of parameters
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