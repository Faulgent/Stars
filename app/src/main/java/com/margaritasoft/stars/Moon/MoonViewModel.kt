package com.margaritasoft.stars.Moon

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.margaritasoft.stars.network.MoonApi
import com.margaritasoft.stars.network.MyResponse
import com.margaritasoft.stars.network.Observer
import com.margaritasoft.stars.network.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class MoonViewModel : ViewModel() {
    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    init {
        requestUpdate()
    }

    fun requestUpdate(latitude: Double = 6.56774, longitude: Double = 79.88956) {
        //println(Moshi.Builder().build().adapter(RequestBody::class.java).toJson(RequestBody()))
        MoonApi.retrofitService.getProperties(
            RequestBody(
                observer = Observer(
                    latitude = latitude,
                    longitude = longitude
                )
            )
        ).enqueue(
            object : Callback<MyResponse> {
                override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
                    _response.value = response.body()?.data?.imageUrl
                    println("${_response.value}")
                }

                override fun onFailure(call: Call<MyResponse>, t: Throwable) {

                    Log.d(
                        "requestFailure",
                        (StringBuilder().append("Failure: ").append(t.toString()).toString())
                    )
                }
            }

        )
    }
}