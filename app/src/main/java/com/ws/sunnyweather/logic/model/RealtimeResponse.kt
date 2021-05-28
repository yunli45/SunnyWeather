package com.ws.sunnyweather.logic.model

// 数据模型
// 一个花括号就是一个类
/**
 * desc: 一个城市实时的天气数据, 如果不用花括号括起来，相当于顶层文件，能被其他的文件访问到（Result）
 *
{
    "status": "ok",
    "result": {
        "realtime": {
            "temperature": 23.16,
            "skycon": "WIND",
            "air_quality": {
            "aqi": { "chn": 17.0 }
    }
}
}}
 */
data class RealtimeResponse(val status: String, val result: Result) {
    data class Result(val realtime: Realtime)
    data class Realtime(val skycon: String, val temperature: Float, val airQuality: AirQuality)
    data class AirQuality(val aqi: AQI)
    data class AQI(val chn: Float)
}