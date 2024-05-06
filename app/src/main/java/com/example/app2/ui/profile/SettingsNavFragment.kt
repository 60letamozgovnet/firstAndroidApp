package com.example.app2.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.app2.R

class settingsNavFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var theme: Int //0 - light 1 -dark
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings_nav, container, false)
        val btn: ImageView = view.findViewById(R.id.switch_theme)
        theme = 0
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
            }
            else if (theme == 1) {
                btn.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.switch_theme2))
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        return view

    }
}