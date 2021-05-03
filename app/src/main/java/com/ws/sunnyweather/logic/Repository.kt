package com.ws.sunnyweather.logic

import android.content.Context
import androidx.lifecycle.liveData
import com.ws.sunnyweather.logic.model.Place
import com.ws.sunnyweather.logic.model.Weather
import com.ws.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext


object Repository {
    /**
     * 众所周知，Android是
    不允许在主线程中进行网络请求的，诸如读写数据库之类的本地数据操作也是不建议在主线程
    中进行的，因此非常有必要在仓库层进行一次线程转换
     */
    /* fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
         val result = try {
             val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
             if (placeResponse.status == "ok") {
                 val places = placeResponse.places
                 Result.success(places)
             } else {
                 Result.failure(
                     RuntimeException(
                         "response status is\n" +
                                 " ${placeResponse.status}"
                     )
                 )
             }
         } catch (e: Exception) {
             Result.failure<List<Place>>(e)
         }
         emit(result)

     }*/

    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    /**
    在仓库层我们并没有提供两个分别用于获取实时天气信息和未来天气信息的方法，而是
    提供了一个refreshWeather()方法用来刷新天气信息。因为对于调用方而言，需要调用两次
    请求才能获得其想要的所有天气数据明显是比较烦琐的行为，因此最好的做法就是在仓库层再
    进行一次统一的封装。
    不过，获取实时天气信息和获取未来天气信息这两个请求是没有先后顺序的，因此让它们并发
    执行可以提升程序的运行效率，但是要在同时得到它们的响应结果后才能进一步执行程序。
     */

    /* fun refreshWeather(lng: String, lat: String) = liveData(Dispatchers.IO) {
         val result = try {
             coroutineScope {
                 val deferredRealtime = async {
                     SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
                 }
                 val deferredDaily = async {
                     SunnyWeatherNetwork.getDailyWeather(lng, lat)
                 }
                 val realtimeResponse = deferredRealtime.await()
                 val dailyResponse = deferredDaily.await()
                 if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                     val weather =
                         Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                     Result.success(weather)
                 } else {
                     Result.failure(
                         java.lang.RuntimeException(
                             "realtime response status is ${realtimeResponse.status}\" +\n" +
                                     " \"daily response status is ${dailyResponse.status}"
                         )
                     )
                 }

             }

         } catch (e: Exception) {
             Result.failure<Weather>(e)
         }
         emit(result)
     }*/

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(
                    realtimeResponse.result.realtime,
                    dailyResponse.result.daily
                )
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    // 需要在函数类型前声明一个
    //suspend关键字，以表示所有传入的Lambda表达式中的代码也是拥有挂起函数上下文的
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}
