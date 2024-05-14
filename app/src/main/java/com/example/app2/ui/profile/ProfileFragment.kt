package com.example.app2.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.app2.R
import com.example.app2.databinding.FragmentProfileBinding
import com.example.app2.retrofit.MainApi
import com.google.android.material.navigation.NavigationView
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class ProfileFragment: Fragment(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    private var _binding: FragmentProfileBinding? = null
//    private lateinit var Api: MainApi
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        drawerLayout = binding.fragmentProfile
        val navigationView = binding.navView

        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            requireActivity(),
            drawerLayout,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.openDrawerButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        replaceFragment(NavFragment())

//        if (savedInstanceState == null) {
//            // Если фрагменты еще не были добавлены
//            replaceFragment(NavFragment()) // Заменяем текущий фрагмент на homeNavFragment
//        }


        return binding.root

    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> replaceFragment(NavFragment())
            R.id.nav_settings -> replaceFragment(SettingsNavFragment())
            R.id.nav_login -> replaceFragment(LoginFragment())
        }
        drawerLayout.closeDrawer(GravityCompat.END)
        return true
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

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}