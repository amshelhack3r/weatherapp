package com.example.weatherapp.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.weatherapp.adapter.CityItemRecyclerViewAdapter
import com.example.weatherapp.R
import com.example.weatherapp.model.Forecast
import com.example.weatherapp.model.FutureForecast
import com.example.weatherapp.util.SHARED_PREF
import com.example.weatherapp.util.buildFiveDayForecastUrl
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_city_list_list.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * A fragment representing a list of Items.
 */
class CityListFragment : Fragment(),
    CityItemRecyclerViewAdapter.onRowListener, CityItemRecyclerViewAdapter.onCloseListener, View.OnClickListener, AdapterView.OnItemClickListener {

    //lazy initialization of shared preferences
    private val preferences: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }

    private val popularCities:Array<String> by lazy {
        resources.getStringArray(R.array.popular_cities)
    }


    private var columnCount = 1
    private val listener: CityItemRecyclerViewAdapter.onRowListener = this
    private val closeListener: CityItemRecyclerViewAdapter.onCloseListener = this

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_list_list, container, false)
   }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        cityrecycler.layoutManager=layoutManager
        cityrecycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        cityrecycler.isNestedScrollingEnabled = false
        val values = preferences.getStringSet("bookmarks", mutableSetOf()) as MutableSet<String>
        cityrecycler.adapter =
            CityItemRecyclerViewAdapter(
                values,
                listener,
                closeListener
            )

        mapLauncher.setOnClickListener(this)

        val adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, popularCities)
        autoCompleteTextView.threshold = 1
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.onItemClickListener = this
    }

    override fun onRowClick(position: Int, cityName:String) {

        val fragment = CityFragment.newInstance(cityName)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onClick(p0: View?) {
        var transaction = requireActivity().supportFragmentManager.beginTransaction()
        val mapFragment = MapsFragment()
        transaction.replace(R.id.content, mapFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val city = p0?.getItemAtPosition(p2).toString()
        val fragment = CityFragment.newInstance(city)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun closeClicked(position: Int, city: String) {
        val bookmarks = preferences.getStringSet("bookmarks", mutableSetOf())
        bookmarks?.remove(city)
        preferences.edit().apply {
            putStringSet("bookmarks", bookmarks)
            apply()
        }
    }


}