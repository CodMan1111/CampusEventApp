package com.example.campuseventapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Event(
    val title: String,
    val club: String,
    val dateTime: String,
    val location: String
)

@Composable
fun StudentHomeScreen() {
    val sampleEvents = listOf(
        Event(
            title = "Career Fair",
            club = "Business Club",
            dateTime = "Oct 26, 2024, 7:00 PM",
            location = "Computing and Engineering"
        ),
        Event(
            title = "Coding Workshop",
            club = "Computing Club",
            dateTime = "Oct 28, 2026, 3:00 PM",
            location = "Computing and Engineering"
        ),
        Event(
            title = "Hackathon",
            club = "Computing Club",
            dateTime = "Oct 29, 2026, 9:00 AM",
            location = "Computing and Engineering"

        ),
        Event(
            title = "Wellness Workshop",
            club = "Gym Club",
            dateTime = "Nov 1, 2026, 2:30 PM",
            location = "RecWell Center"
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            SearchSelection()

            Spacer(modifier = Modifier.height(12.dp))

            FilterSection()

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(sampleEvents) { event ->
                    EventCard(event = event)
                }
            }
        }
        StudentBottomNav()
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

            Text(
                text = "Search events...",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun FilterSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterByChip(text = "All", selected = true)
        FilterByChip(text = "Today", selected = false)
        FilterByChip(text = "This Week", selected = false)
        FilterByChip(text = "My Club", selected = false)
    }
}

@Composable
fun FilterByChip(text: String, selected: Boolean) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = if (selected) Color(0xFFDDE3F8) else Color.White,
        tonalElevation = 2.dp
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = Color(0xFF2C2C2C)
        )
    }
}

@Composable
fun EventCard(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
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
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = event.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF202028)
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = event.dateTime,
                    fontSize = 15.sp,
                    color = Color(0xFF2F2F35)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = event.location,
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }
            Icon(
                imageVector = Icons.Default.BookmarkBorder,
                contentDescription = "Save Event",
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun StudentBottomNav() {
    NavigationBar(
        containerColor = Color.White
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Search") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.People, contentDescription = "Friends") },
            label = { Text("Friends") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Notifications") },
            label = { Text("Alerts") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") }
        )
    }
}