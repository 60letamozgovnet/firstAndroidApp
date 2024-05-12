package com.example.app2

import com.example.app2.retrofit.Auth
import com.example.app2.retrofit.BookDb
import com.example.app2.retrofit.MainApi
import com.example.app2.retrofit.TknBookDb
import com.example.app2.retrofit.Token
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class GlobalDataBase {
    val retrofit = Retrofit.Builder()
        //.baseUrl("https://dummyjson.com/")
        .baseUrl("https://bobonch.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val Api = retrofit.create(MainApi::class.java)

    suspend fun logIn(auth: Auth): String {
        var response: String = "-1"
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

    suspend fun getBooks(): List<BookDb> = withContext(Dispatchers.IO) {
        val response = Api.getBooks()
        response
    }

    suspend fun addBook(book: BookDb): Int = withContext(Dispatchers.IO) {
        val token: String = getDataFromFile("token_bearer.txt")
        val response = Api.insBook(TknBookDb(
            Token(token),
            book
        ))
        response
    }

    fun getDataFromFile(file: String): String {
        var fileInputStream: FileInputStream? = null
        fileInputStream = MainActivity().openFileInput(file)
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)

        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine(); text }() != null) {
            stringBuilder.append(text)
            stringBuilder.append("\n")

        }

        return stringBuilder.toString()
    }
}
