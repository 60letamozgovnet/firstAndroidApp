package com.example.testingbd.retrofit

import com.example.testingbd.dat
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MainApi {
    @POST("login")
    suspend fun log(@Body authR: Auth): Int  // List<dat>

    @POST("register")
    suspend fun reg(@Body authR: Auth): Int  // List<dat>

    /*
    @GET("products/1")
     suspend fun getInd(): Product

    @POST("auth/login")
    suspend fun auth(@Body authR: AuthReg): User

    @FormUrlEncoded
    POST("token")
    suspend fun auth2(@Field("username") fld: String, @Field("password") fld2: String): List<dat>
    suspend fun auth2(@Body authR: AuthReg): List<dat>
     */
}
