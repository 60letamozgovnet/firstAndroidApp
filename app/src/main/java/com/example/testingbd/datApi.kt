package com.example.testingbd
import retrofit2.Call
import retrofit2.http.GET

interface datApi {
    @GET("/items")
    suspend fun getLs(): List<dat>
    //fun getLs(): Call<List<dat>>
}