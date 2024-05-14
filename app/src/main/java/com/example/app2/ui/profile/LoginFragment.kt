package com.example.app2.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.app2.GlobalDataBase
import com.example.app2.R
import com.example.app2.retrofit.Auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class LoginFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        var username: EditText = view.findViewById(R.id.username)
        val passLine: EditText = view.findViewById(R.id.password)

        println(getDataFromFile("profile.txt").split("\n").size)


        val btnSignIn: Button = view.findViewById(R.id.signIn)
        val btnLogIn: Button = view.findViewById(R.id.logIn)
        var token: String = getDataFromFile("token_bearer.txt")
        btnSignIn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                token = GlobalDataBase().logIn(Auth(username.text.toString(), passLine.text.toString()))
            }
            rewriteFile("profile.txt", username.text.toString())
            rewriteFile("token_bearer.txt", token)
        }
        btnLogIn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if (GlobalDataBase().regI(Auth(username.text.toString(), passLine.text.toString())) == 0){
                    token = GlobalDataBase().logIn(Auth(username.text.toString(), passLine.text.toString()))
                }
                else{
                    token = "NAN"
                }
                rewriteFile("token_bearer.txt", token)
                rewriteFile("profile.txt", username.text.toString())
            }
        }



        return view
    }

    fun rewriteFile(file: String, data: String) {
        val fileOutputStream: FileOutputStream
        // https://stackoverflow.com/questions/4015773/the-method-openfileoutput-is-undefined
        fileOutputStream = requireActivity().openFileOutput(file, Context.MODE_PRIVATE)
        fileOutputStream.write(data.toByteArray())

        println("Rewrite file")
        println(data)
    }

    fun getDataFromFile(file: String): String {
        var fileInputStream: FileInputStream? = null
        fileInputStream = requireActivity().openFileInput(file)
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