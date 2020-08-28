package com.example.weatherapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.R
import com.example.weatherapp.fragment.CityListFragment
import com.example.weatherapp.fragment.SettingsFragment
import com.example.weatherapp.fragment.WebView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))



        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = CityListFragment()
        fragmentTransaction.replace(R.id.content, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

//        MyThread(handler).start()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.help -> {
                val transaction = supportFragmentManager.beginTransaction()
                val fragment = WebView()
                transaction.replace(R.id.content, fragment)
                transaction.addToBackStack(null)
                transaction.commit()

                true
            }
            R.id.setting -> {
                val transaction = supportFragmentManager.beginTransaction()
                val fragment = SettingsFragment()
                transaction.replace(R.id.content, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}