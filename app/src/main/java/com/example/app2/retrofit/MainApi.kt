package com.example.app2.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MainApi {
    @POST("login")
    suspend fun log(@Body authR: Auth): String

    @POST("register")
    suspend fun reg(@Body authR: Auth): Int 

    @POST("insertBook")
    suspend fun insBook(@Body tknBook: TknBookDb): Int

    @GET("books")
    suspend fun getBooks(): List<BookDb>
}
