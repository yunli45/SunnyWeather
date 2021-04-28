package com.ws.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ws.sunnyweather.logic.Repository
import com.ws.sunnyweather.logic.model.Place

class PlaceViewModel :ViewModel(){
    private val searchLiveData = MutableLiveData<String>()
    val placeList = ArrayList<Place>() // 用于对界面上显示的城市数据进行缓存

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}

