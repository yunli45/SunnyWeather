package com.ws.sunnyweather.logic.network

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit构建器 单例类
 */
object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * 外部可见的create()方法，并接收一个Class类型的参数。当在外部调
    用这个方法时，实际上就是调用了Retrofit对象的create()方法，从而创建出相应Service接
    口的动态代理对象
    val appService = ServiceCreator.create(AppService::class.java)
     */
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    /**
     * 又定义了一个不带参数的create()方法，并使用inline关键字来修饰方法，
        使用reified关键字来修饰泛型，这是泛型实化的两大前提条件
        那么现在我们就又有了一种新的方式来获取AppService接口的动态代理对象
    val appService = ServiceCreator.create<AppService>()
     */
    inline fun <reified T> create(): T = create(T::class.java)

}