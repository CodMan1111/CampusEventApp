package com.example.campuseventapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.campuseventapp.ui.theme.CampusEventAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusEventAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    //WelcomeScreen()
                    StudentHomeScreen()
                }
            }
        }
    }
}
