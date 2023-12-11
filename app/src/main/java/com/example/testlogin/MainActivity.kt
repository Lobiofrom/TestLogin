package com.example.testlogin

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController


const val PREFS = "prefs"
const val TOKEN = "token"

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

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val sharedPrefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val token = sharedPrefs.getString(TOKEN, "")

        if (token.isNullOrEmpty()) {
            navController.navigate(R.id.loginFragment)
        } else {
            navController.navigate(R.id.loggedFragment)
        }
    }
}