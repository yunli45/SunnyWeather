package com.ws.sunnyweather.logic.network

import com.ws.sunnyweather.config.SunnyWeatherApplication
import com.ws.sunnyweather.logic.dao.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}