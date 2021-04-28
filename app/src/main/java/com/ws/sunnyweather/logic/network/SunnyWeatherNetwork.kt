package com.ws.sunnyweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * 一个统一的网络数据源访问入口，对所有网络请求的API进行封装
 */
object SunnyWeatherNetwork {
    private val placeService = ServiceCreator.create<PlaceService>()

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    /**
     * 首先await()函数仍然是一个挂起函数，然后我们给它声明了一个泛型T，并将await()函数定义成了Call<T>的扩展函数，这样
    所有返回值是Call类型的Retrofit网络请求接口就都可以直接调用await()函数了。
     */
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response is null"))
                }

            })

        }
    }

}