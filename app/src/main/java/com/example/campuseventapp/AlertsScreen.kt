package com.example.campuseventapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun AlertsScreen(navController: NavController, isAdvisor: Boolean, viewModel: EventListViewModel = viewModel()) {
    val events by viewModel.events.collectAsState()

    val alertEvents = if (isAdvisor) emptyList() else {
        events.filter { event ->
            val category = event.category ?: return@filter false
            SubscriptionState.isSubscribed(category)
        }
    }

    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        bottomBar = {
            if (isAdvisor) {
                AdvisorBottomNav(navController)
            } else {
                StudentBottomNav(navController)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .statusBarsPadding()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Alerts",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (alertEvents.isEmpty()) {
                Text(
                    text = if (isAdvisor) "No alerts for advisors." else "No alerts. Subscribe to categories in your profile.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(alertEvents) { event ->
                        EventCard(event = event)
                    }
                }
            }
        }
    }
}