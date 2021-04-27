package com.ws.sunnyweather.logic.dao

import com.google.gson.annotations.SerializedName

data class PlaceResponse(val status: String, val places: List<Place>)
data class Place(
    val name: String,
    val location: Location,
    //了@SerializedName注解的方式，来让JSON字段和Kotlin字段之间建立映射关系
    @SerializedName("formatted_address") val address: String
)

data class Location(val lng: String, val lat: String)