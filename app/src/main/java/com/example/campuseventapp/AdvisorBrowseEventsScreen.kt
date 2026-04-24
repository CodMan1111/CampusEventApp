package com.example.campuseventapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Arrangement

@Composable
fun AdvisorBrowseEventsScreen(navController: NavController, viewModel: EventListViewModel = viewModel()) {
    val events by viewModel.events.collectAsState()
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
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Text(
                text = "Advisor Dashboard",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF202028)
            )
            Spacer(modifier = Modifier.height(20.dp))

            SearchSelection()

            Spacer(modifier = Modifier.height(12.dp))

            FilterSelection()

            Spacer(modifier = Modifier.height(16.dp))

            if (events.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(events) { event ->
                        EventCard(
                            event = event,
                            onClick = { }
                        )
                    }
                }
            }
        }
    }
}