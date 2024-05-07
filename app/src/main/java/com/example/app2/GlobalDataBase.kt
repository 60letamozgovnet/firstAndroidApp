package com.example.app2

import com.example.app2.retrofit.Auth
import com.example.app2.retrofit.MainApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GlobalDataBase {
    val retrofit = Retrofit.Builder()
        //.baseUrl("https://dummyjson.com/")
        .baseUrl("https://bobonch.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val Api = retrofit.create(MainApi::class.java)

    suspend fun logIn(auth: Auth): Int {
        var response: Int = -1
        response = Api.log(auth)
//    CoroutineScope(Dispatchers.IO).launch {
//
//    }

        return response
    }

    suspend fun regI(auth: Auth): Int = withContext(Dispatchers.IO) {
        val response = Api.reg(auth)
        response
    }

    suspend fun getBooks(): List<Book> = withContext(Dispatchers.IO) {
        val response = Api.getBooks()
        response
    }

    suspend fun addBook(book: Book): Int = withContext(Dispatchers.IO) {
        val response = Api.insBook(book)
        response
    }

    fun test(): Int {
        return 0
    }
}
