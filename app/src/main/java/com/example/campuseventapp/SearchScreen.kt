package com.example.campuseventapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun SearchScreen(
    navController: NavController,
    isAdvisor: Boolean,
    viewModel: EventListViewModel = viewModel()
) {
    val events by viewModel.events.collectAsState()
    var searchText by remember { mutableStateOf("") }

    val searchedEvents =
        if (searchText.isBlank()) {
            emptyList()
        } else {
            events.filter { event ->
                val query = searchText.trim()

                query.isEmpty() ||
                        event.title?.contains(query, ignoreCase = true) == true ||
                        event.location?.contains(query, ignoreCase = true) == true ||
                        event.dateTime?.contains(query, ignoreCase = true) == true ||
                        event.category?.contains(query, ignoreCase = true) == true
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
                .padding(horizontal = 16.dp, vertical = 20.dp),
        ) {
            Text(
                text = "Search Events",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF202028)
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Search by title, category, location, or date") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            if (searchText.isBlank()) {
                Box(
                    modifier = Modifier.fillMaxSize()
                )
            } else if (searchedEvents.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No matching events found",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(searchedEvents) { event->
                        EventCard(event  = event)
                    }
                }
            }
        }
    }
}