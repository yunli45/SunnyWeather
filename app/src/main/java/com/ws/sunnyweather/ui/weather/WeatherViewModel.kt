package com.ws.sunnyweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ws.sunnyweather.logic.Repository
import com.ws.sunnyweather.logic.model.Location

/**
 * 定义了一个refreshWeather()方法来
刷新天气信息，并将传入的经纬度参数封装成一个Location对象后赋值给
locationLiveData对象，然后使用Transformations的switchMap()方法来观察这个对
象，并在switchMap()方法的转换函数中调用仓库层中定义的refreshWeather()方法。这
样，仓库层返回的LiveData对象就可以转换成一个可供Activity观察的LiveData对象了。

 */
class WeatherViewModel : ViewModel() {
    private val locationLiveData = MutableLiveData<Location>()
    /**
     * 我们还在WeatherViewModel中定义了 locationLng、locationLat和placeName
    这3个变量，它们都是和界面相关的数据，放到ViewModel中可以保证它们在手机屏幕发生旋
    转的时候不会丢失，稍后在编写UI层代码的时候会用到这几个变量
     */
    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        Repository.refreshWeather(location.lng, location.lat)
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }
}