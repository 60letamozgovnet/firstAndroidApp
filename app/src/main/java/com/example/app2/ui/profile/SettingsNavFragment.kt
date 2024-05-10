package com.example.app2.ui.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.app2.R
import java.io.FileOutputStream

class SettingsNavFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var theme: Int //0 - light 1 -dark
        theme = 0
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings_nav, container, false)
        val btn: ImageView = view.findViewById(R.id.switch_theme)
        if (theme == 1) {
            btn.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.switch_theme))
        }
        else if (theme == 0) {
            btn.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.switch_theme2))
        }
        btn.setOnClickListener{
            if (theme == 0) {
                btn.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.switch_theme))
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                theme = 1
                rewriteFile("theme.txt", "1")
            }
            else if (theme == 1) {
                btn.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.switch_theme2))
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                theme = 0
                rewriteFile("theme.txt", "0")
            }
        }
        return view

    }

//    fun getDataFromFile(file: String): String {
//        var fileInputStream: FileInputStream? = null
//        fileInputStream = requireActivity().openFileInput(file)
//        val inputStreamReader = InputStreamReader(fileInputStream)
//        val bufferedReader = BufferedReader(inputStreamReader)
//
//        val stringBuilder: StringBuilder = StringBuilder()
//        var text: String? = null
//        while ({ text = bufferedReader.readLine(); text }() != null) {
//            stringBuilder.append(text)
//            stringBuilder.append("\n")
//
//        }
//
//        return stringBuilder.toString()
//    }
//
//
//    fun appendNewLine(file: String, data: String) {
//        val fileOutputStream: FileOutputStream
//        fileOutputStream = requireActivity().openFileOutput(file, Context.MODE_APPEND)
//        fileOutputStream.write(data.toByteArray())
//        fileOutputStream.write("\n".toByteArray())
//
//        println("Append new line inside file")
//        println(data)
//    }

    fun rewriteFile(file: String, data: String) {
        val fileOutputStream: FileOutputStream
        // https://stackoverflow.com/questions/4015773/the-method-openfileoutput-is-undefined
        fileOutputStream = requireActivity().openFileOutput(file, Context.MODE_PRIVATE)
        fileOutputStream.write(data.toByteArray())
        fileOutputStream.write("\n".toByteArray())

        println("Rewrite file")
        println(data)
    }
}