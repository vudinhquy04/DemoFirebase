package com.quyvd.demofirebase

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface APIService {
    @get:GET("/api/list")
    val cars: Call<List<CarModel?>?>?


    @POST("/api/add_xe")
    fun addCar(@Body xe: CarModel?): Call<List<CarModel?>?>?

    @PUT("/api/update_xe")
    fun updateCar(@Body xe: CarModel?): Call<List<CarModel?>?>?

    @DELETE("/api/xoa")
    fun deleteCar(id: String?): Call<List<CarModel?>?>?

    companion object {
        const val DOMAIN: String = "http://10.24.41.173:3030/" // localhost
    }
}
