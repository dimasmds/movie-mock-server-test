package com.riotfallen.moviesearch

import android.app.Application
import android.view.View
import com.androidnetworking.AndroidNetworking

open class MainApp: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(this.applicationContext)
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}