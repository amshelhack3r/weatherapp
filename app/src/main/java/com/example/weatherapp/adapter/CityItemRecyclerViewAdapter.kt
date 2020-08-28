package com.example.weatherapp.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.weatherapp.R


class CityItemRecyclerViewAdapter(
    private var values: MutableSet<String>,
    val listener: onRowListener,
    val closeListener: onCloseListener
) : RecyclerView.Adapter<CityItemRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_city, parent, false)
        listener
        return ViewHolder(view, listener, closeListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idView.text = values.elementAt(position)
    }

    fun removeItem(city:String){
        values.remove(city)
        notifyItemRemoved(values.indexOf(city))
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View, val rowlistener: onRowListener, val onCloseListener: onCloseListener) : RecyclerView.ViewHolder(view), View.OnClickListener {
        init {
            val closeImg: ImageView = view.findViewById(R.id.closeBtn)
            closeImg.setOnClickListener(View.OnClickListener {
                onCloseListener.closeClicked(adapterPosition, idView.text.toString())
                removeItem(idView.text.toString())
            })
            view.setOnClickListener(this)
        }
        val idView: TextView = view.findViewById(R.id.cityName)

        override fun onClick(p0: View?) {
            rowlistener.onRowClick(adapterPosition, idView.text.toString())
        }
    }

    interface onRowListener{
        fun onRowClick(position:Int, city:String)
    }
    interface onCloseListener{
        fun closeClicked(position: Int, city: String)
    }




}