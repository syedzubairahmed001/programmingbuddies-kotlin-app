package com.example.programmingbuddies.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.programmingbuddies.R
import com.example.programmingbuddies.databinding.ActivityMainBinding
import com.example.programmingbuddies.util.makeStatusBarTransparent
import com.example.programmingbuddies.util.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navController: NavController
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeStatusBarTransparent()

        navController = findNavController(R.id.fragment)
        bottomNav = findViewById(R.id.bottomNavMenu)
        bottomNav.setupWithNavController(navController)

    }
}