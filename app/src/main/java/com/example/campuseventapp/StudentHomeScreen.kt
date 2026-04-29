package com.example.campuseventapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.navigation.NavController
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StudentHomeScreen(
    navController: NavController,
    email: String = "",
    viewModel: EventListViewModel = viewModel(),
    onEventClick: (String) -> Unit = {}
) {
    val events by viewModel.events.collectAsState()
    var selectedFilter by remember { mutableStateOf("All") }

    val filteredEvents = when (selectedFilter) {
        "All" -> events
        "Career" -> events.filter {
            it.title?.contains("Career", ignoreCase = true) == true
        }
        "Tech" -> events.filter {
            it.title?.contains("Coding", ignoreCase = true) == true ||
                    it.title?.contains("Hackathon", ignoreCase = true) == true
        }
        "Wellness" -> events.filter {
            it.title?.contains("Wellness", ignoreCase = true) == true
        }
        else -> events
    }

    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        bottomBar = { StudentBottomNav(navController, email) }
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
                text = "Browse Events",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF202028)
            )
            Spacer(modifier = Modifier.height(20.dp))

            FilterSelection(
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (events.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No events found", fontSize = 16.sp, color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(filteredEvents) { event ->
                        EventCard(
                            event = event,
                            onClick = { onEventClick(event.eventID) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchSelection() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFE9E9F2)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.DarkGray
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Search events...", color = Color.Gray, fontSize = 16.sp)
        }
    }
}

@Composable
fun FilterSelection(
    selectedFilter: String = "All",
    onFilterSelected: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterByChip(text = "All", selectedFilter, onFilterSelected)
        FilterByChip(text = "Career", selectedFilter, onFilterSelected)
        FilterByChip(text = "Tech", selectedFilter, onFilterSelected)
        FilterByChip(text = "Wellness", selectedFilter, onFilterSelected)
    }
}

@Composable
fun FilterByChip(
    text: String,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = if (selectedFilter == text) Color(0xFFDDE3F8) else Color.White,
        tonalElevation = 2.dp,
        modifier = Modifier.clickable { onFilterSelected(text) }
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
            fontSize = 14.sp,
            fontWeight = if (selectedFilter == text) FontWeight.Bold else FontWeight.Normal,
            color = Color(0xFF2C2C2C)
        )
    }
}

@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit = {},
    onDelete: (() -> Unit)? = null  // ADD THIS
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.title ?: "No title",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF202028)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = event.dateTime ?: "No date", fontSize = 15.sp, color = Color(0xFF2F2F35))
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = event.location ?: "No location", fontSize = 15.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Icon(
                    imageVector = Icons.Default.BookmarkBorder,
                    contentDescription = "Save Event",
                    tint = Color.Gray
                )
                // ADD THIS
                if (onDelete != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Event",
                            tint = Color(0xFFE53935)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StudentBottomNav(navController: NavController, email: String = "") {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.StudentHome.route + "?email=${AppState.currentUserEmail}") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.StudentSearch.route) },
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Search") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.StudentFriends.route) },
            icon = { Icon(Icons.Default.People, contentDescription = "Friends") },
            label = { Text("Friends") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.StudentAlerts.route) },
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Alerts") },
            label = { Text("Alerts") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.StudentProfile.createRoute(AppState.currentUserEmail)) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") }
        )
    }
}

@Composable
fun AdvisorBottomNav(navController: NavController, email: String = "") {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.AdvisorHome.route) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.AdvisorSearch.route) },
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Search") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.CreateEvent.route) },
            icon = { Icon(Icons.Default.Add, contentDescription = "Create") },
            label = { Text("Create") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.AdvisorAlerts.route) },
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Alerts") },
            label = { Text("Alerts") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.AdvisorProfile.createRoute(AppState.currentUserEmail)) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") }
        )
    }
}