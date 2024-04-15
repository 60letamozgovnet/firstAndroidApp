package com.example.testingbd

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.testingbd.retrofit.AuthReg
import com.example.testingbd.retrofit.Product
import com.example.testingbd.retrofit.MainApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
//import com.zaxxer.hikari.HikariConfig
//import com.zaxxer.hikari.HikariDataSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val tv = findViewById<TextView>(R.id.textView2)
        val b = findViewById<Button>(R.id.button2)

        val retrofit = Retrofit.Builder()
            //.baseUrl("https://dummyjson.com/")
            .baseUrl("https://bobonch.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val productApi = retrofit.create(MainApi::class.java)

        //var sd: AuthReg =
        //sd.password = "asdf"

        b.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val product = productApi.auth(
                    AuthReg(
                        "delsdfa",
                        "ssh"
                    )

                )
                runOnUiThread {
                    println(product)
                    //tv.text = product[0].name
                }
            }
        }



    }


    /*
    fun main2() {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:postgresql://localhost:5432/mydb"
        config.username = "myuser"
        config.password = "Forsash9"
        config.maximumPoolSize = 10 // Максимальное количество соединений в пуле

        val dataSource = HikariDataSource(config)

        try {
            // Получение соединения из пула
            val connection: Connection = dataSource.connection

            println("Успешное подключение к базе данных!")

            // Выполнение запросов, обработка данных и т.д.

            connection.close() // Возврат соединения в пул
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            dataSource.close() // Закрытие пула соединений
        }
    }
    */

    /*
    fun main2() {
        // Создаем экземпляр Retrofit
        val retrofit = Retrofit.Builder()
            //.baseUrl("http://127.0.0.1:8000") // Замените URL на ваше API
            .baseUrl("https://bobonch.onrender.com/") // З
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Создаем интерфейс для описания API методов
        val service = retrofit.create(datApi::class.java)

        // Вызываем метод API
        val call = service.getLs()

        // Выполняем запрос асинхронно
        call.enqueue(object : retrofit2.Callback<List<dat>> {
            override fun onResponse(call: retrofit2.Call<List<dat>>, response: retrofit2.Response<List<dat>>) {
                if (response.isSuccessful) {
                    val posts = response.body()
                    posts?.forEach {
                        println(it.id)
                    }
                } else {
                    println("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<List<dat>>, t: Throwable) {
                println("Failed to execute request: ${t.message}")
            }
        })
    }

     */


    /*
    fun main3() {
        // Создаем экземпляр Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bobonch.onrender.com") // Замените URL на ваше API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Создаем интерфейс для описания API методов
        val service = retrofit.create(datApi::class.java)

        // Вызываем метод API
        val call = service.getLs()

        // Выполняем запрос асинхронно
        call.enqueue(object : retrofit2.Callback<List<dat>> {
            override fun onResponse(call: retrofit2.Call<List<dat>>, response: retrofit2.Response<List<dat>>) {
                if (response.isSuccessful) {
                    val posts = response.body()
                    posts?.forEach {
                        println("Title: ${it.title}, Body: ${it.body}")
                    }
                } else {
                    println("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<List<dat>>, t: Throwable) {
                println("Failed to execute request: ${t.message}")
            }
        })
    }
     */
    /*
    // Модель данных для постов
    data class Post(
        val userId: Int,
        val id: Int,
        val title: String,
        val body: String
    )

    // Интерфейс для описания API методов
    interface ApiService {
        @GET("/posts")
        fun getPosts(): retrofit2.Call<List<Post>>
    }
     */
    /*
    // Модель данных для постов
    data class Post(
        val userId: Int,
        val id: Int,
        val title: String,
        val body: String
    )

    // Интерфейс для описания API методов

    interface ApiService {
        @GET("/posts")
        fun getPosts(): retrofit2.Call<List<Post>>
    }
    */


    //fun clkBut(view: View) {
        //main3()

    //}


    /*
    String url = "jdbc:postgresql://localhost/test";
    Properties props = new Properties();
    props.setProperty("user", "fred");
    props.setProperty("password", "secret");
    props.setProperty("ssl", "true");
    Connection conn = DriverManager.getConnection(url, props);
     */

    /*
    val url: String = "jdbc:postgresql://localhost/mydb"
    val props = Properties().apply {
        setProperty("user", "myuser")
        setProperty("password", "Forsash9")
        //setProperty("ssl", "true")
    }
    val conn = DriverManager.getConnection(url, props)
     */

}