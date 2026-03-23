package com.kadyrova.fakenewsdetector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kadyrova.fakenewsdetector.screens.HomeScreen
import com.kadyrova.fakenewsdetector.ui.theme.FakenewsdetectorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FakenewsdetectorTheme {
                HomeScreen()
            }
        }
    }
}