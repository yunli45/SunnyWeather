package com.ws.sunnyweather.logic

import androidx.lifecycle.liveData
import com.ws.sunnyweather.logic.model.Place
import com.ws.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers


object Repository {
    /**
     * 众所周知，Android是
    不允许在主线程中进行网络请求的，诸如读写数据库之类的本地数据操作也是不建议在主线程
    中进行的，因此非常有必要在仓库层进行一次线程转换
     */
     fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
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

    }
}
