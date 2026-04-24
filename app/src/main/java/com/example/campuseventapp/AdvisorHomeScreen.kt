package com.example.campuseventapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AdvisorHomeScreen(navController: NavController) {
    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        bottomBar = { AdvisorBottomNav(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .statusBarsPadding()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Advisor Dashboard",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}