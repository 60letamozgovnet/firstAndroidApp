package com.example.app2.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.app2.R
import com.example.app2.databinding.FragmentProfileBinding
import com.example.app2.retrofit.Auth
import com.example.app2.retrofit.MainApi
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileFragment: Fragment(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    private var _binding: FragmentProfileBinding? = null
    private lateinit var Api: MainApi
    private val binding get() = _binding!!

    private fun initRetro() {
        val retrofit = Retrofit.Builder()
            //.baseUrl("https://dummyjson.com/")
            .baseUrl("https://bobonch.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        Api = retrofit.create(MainApi::class.java)
    }

    suspend fun logInto(auth: Auth): Int = withContext(Dispatchers.IO) {
        val response = Api.log(auth)
        response
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        initRetro()

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

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}