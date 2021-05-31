package com.ws.sunnyweather.logic.dao

import androidx.core.content.edit
import com.google.gson.Gson
import com.ws.sunnyweather.config.SunnyWeatherApplication
import com.ws.sunnyweather.logic.model.Place

object PlaceDao {
    fun savePlace(place: Place) {
        // google 提供的 ktx 扩展库
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }
    fun getSavedPlace(): Place{
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }
    // 用于判断是否有数据已被存储
    fun isPlaceSaved() = sharedPreferences().contains("place")
    private fun sharedPreferences() =
        SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", 0)
}