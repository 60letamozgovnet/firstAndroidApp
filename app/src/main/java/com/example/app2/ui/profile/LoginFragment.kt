package com.example.app2.ui.profile

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

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val username: EditText = view.findViewById(R.id.username)
        val passLine: EditText = view.findViewById(R.id.password)
        val btnSignIn: Button = view.findViewById(R.id.signIn)
        btnSignIn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                println( GlobalDataBase().logIn(Auth(username.text.toString(), passLine.text.toString())) )
//                GlobalDataBase().regI(Auth(username.text.toString(), passLine.text.toString()))
            }
        }
        return view
    }

}