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
                NavGraph()
                /*
                Surface(color = MaterialTheme.colorScheme.background) {
                    //WelcomeScreen()
                    //StudentHomeScreen() If want to see the Student screen with the details of the event you use NavGraph()
                    //LoginScreen()
                }
                 */
            }
        }
    }
}
