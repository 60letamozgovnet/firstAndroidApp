private lateinit var Api: MainApi
// inside onCreate
initRetro()

 // b.setOnClickListener {
            lifecycleScope.launch {
                val result =  logI()
                println(result)
                //onResult(result)
            }

private fun initRetro() {
        val retrofit = Retrofit.Builder()
            //.baseUrl("https://dummyjson.com/")
            .baseUrl("https://bobonch.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        Api = retrofit.create(MainApi::class.java)
    }

suspend fun logInto(): Int = withContext(Dispatchers.IO) {
        val response = Api.log(
            Auth(
                "kriil",
                "asdcf",
                //"asdf",
                //"blabla"
            )

        )
        response
        //if (response.success) response.data else response.message
    }


 
