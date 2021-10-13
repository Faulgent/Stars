package com.margaritasoft.stars.network

import com.margaritasoft.stars.BuildConfig
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.text.SimpleDateFormat
import java.util.*

private const val BASE_URL = "https://api.astronomyapi.com"
private const val HASH = BuildConfig.API_KEY

private val moshi = Moshi.Builder()
    .build()
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface MoonApiService {
    @Headers("Authorization: Basic $HASH")
    @POST("/api/v2/studio/moon-phase")
    fun getProperties(@Body requestBody: RequestBody): Call<MyResponse>
}

object MoonApi {
    val retrofitService: MoonApiService by lazy { retrofit.create(MoonApiService::class.java) }
}

data class MyResponse(
    val data: Data
)

data class Data(
    val imageUrl: String
)

data class RequestBody(
    val format: String = "png",
    val style: Style = Style(),
    val observer: Observer = Observer(),
    val view: View = View()
) {}

data class Style(
    val moonStyle: String = "default",
    val backgroundStyle: String = "solid",
    val backgroundColor: String = "black",
    val headingColor: String = "white",
    val textColor: String = "white"
) {

}


data class Observer(
    val latitude: Double = 6.56774,
    val longitude: Double = 79.88956,
    val date: String = getDate()
) {}

val dataFormat = SimpleDateFormat("yyyy-MM-dd")
fun getDate(): String {
    val currentDate: String = dataFormat.format(Date())
    println(currentDate)
    return currentDate
}


data class View(
    val type: String = "portrait-simple"
)
