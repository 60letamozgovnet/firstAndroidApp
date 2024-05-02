package com.example.app2.retrofit

import retrofit2.http.Body
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
