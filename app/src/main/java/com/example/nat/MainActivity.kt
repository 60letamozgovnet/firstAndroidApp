package com.example.nat

import android.R
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.nat.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI(3)
    }

    /**
     * A native method that is implemented by the 'nat' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(int: Int): String

    companion object {
        // Used to load the 'nat' library on application startup.
        init {
            System.loadLibrary("nat")
        }
    }

    fun buttonCl(view: View?) {
        var x = stringFromJNI(3)
        println("hell yeah")
        println(x)
    }

    fun onClk(view: View) {
        println("hell yea")
    }
}


