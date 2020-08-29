package com.example.weatherapp.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.weatherapp.R



/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class FutureForecastRecyclerViewAdapter(
    private val values: List<Map<String, Any?>>
) : RecyclerView.Adapter<FutureForecastRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.dayOfWeek.text = item.getValue("dayOfWeek").toString()
        holder.temp.text = item.getValue("temp").toString()
        holder.condition.text = item.getValue("condition").toString()
        holder.deg.text = item.getValue("wind_deg").toString()
        holder.speed.text = item.getValue("wind_speed").toString()
        holder.humidity.text = item.getValue("humidity").toString()
        holder.pressure.text = item.getValue("pressure").toString()
        holder.icon.setImageResource(item.getValue("icon").toString().toInt())
    }


    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayOfWeek: TextView = view.findViewById(R.id.dayOfWeek)
        val temp: TextView = view.findViewById(R.id.future_temp)
        val pressure: TextView = view.findViewById(R.id.future_pressure)
        val humidity: TextView = view.findViewById(R.id.future_humidity)
        val speed: TextView = view.findViewById(R.id.future_wind_speed)
        val deg: TextView = view.findViewById(R.id.future_wind_deg)
        val condition: TextView = view.findViewById(R.id.future_condition)
        val icon: ImageView = view.findViewById(R.id.future_icon)


    }
}