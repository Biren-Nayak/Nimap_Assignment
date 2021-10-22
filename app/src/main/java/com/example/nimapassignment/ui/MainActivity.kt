package com.example.nimapassignment.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.example.nimapassignment.R
import com.example.nimapassignment.network.cacheSize
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Cache

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
    }
    fun toggleDarkMode(view: View) {
        AppCompatDelegate.setDefaultNightMode(
            when(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK){
                Configuration.UI_MODE_NIGHT_YES -> {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                else -> {
                    AppCompatDelegate.MODE_NIGHT_YES
                }
            })
        overridePendingTransition(0, 0)
    }


}