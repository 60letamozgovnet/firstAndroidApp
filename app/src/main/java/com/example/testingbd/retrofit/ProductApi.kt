package com.example.testingbd.retrofit

import com.example.testingbd.dat
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MainApi {
    //@GET("products/1")
   // suspend fun getInd(): Product

    //@POST("auth/login")
    //suspend fun auth(@Body authR: AuthReg): User
    @POST("users/me")
    suspend fun auth(@Body authR: AuthReg): List<dat>
}