package com.example.campuseventapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.campuseventapp.ui.theme.CampusEventAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        NotificationHelper.init(this)
        SubscriptionState.init(this)
        setContent {
            CampusEventAppTheme {
                NavGraph()
            }
        }
    }
}