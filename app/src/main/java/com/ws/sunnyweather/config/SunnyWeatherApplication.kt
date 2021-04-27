package com.ws.sunnyweather.config

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication:Application() {
    // 定义静态变量或着静态方法
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN = "Kd0V6U58a7aXLTZB"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
