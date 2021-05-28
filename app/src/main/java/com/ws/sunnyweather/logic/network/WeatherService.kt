package com.ws.sunnyweather.logic.network

import com.ws.sunnyweather.config.SunnyWeatherApplication
import com.ws.sunnyweather.logic.model.DailyResponse
import com.ws.sunnyweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 定义一个用于访问天气信息API的Retrofit接口
 *  getRealtimeWeather()方法用于获取实时的天气信息，
 *  getDailyWeather()方法用于获取未来的天气信息
 */
interface WeatherService {
    @GET("/v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): Call<RealtimeResponse>

    @GET("/v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>
}