package com.example.campuseventapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun FriendsScreen(
    navController: NavController,
    viewModel: UserListViewModel = viewModel()
) {
    val users by viewModel.users.collectAsState()

    var selectedTab by remember { mutableStateOf("My Friends") }
    val myFriends = remember { mutableStateListOf<User>() }

    val pendingFriends = users.filter { user ->
        myFriends.none { it.email == user.email }
    }
    val displayedUsers =
        if (selectedTab == "My Friends") myFriends
        else pendingFriends

    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        bottomBar = { StudentBottomNav(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .statusBarsPadding()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            Text(
                text = "Friends",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF202028)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                FriendTabButton(
                    text = "My Friends",
                    selected = selectedTab == "My Friends",
                    onClick = { selectedTab = "My Friends" },
                    modifier = Modifier.weight(1f)
                )
                FriendTabButton(
                    text = "Pending Friends",
                    selected = selectedTab == "Pending Friends",
                    onClick = { selectedTab = "Pending Friends" },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (displayedUsers.isEmpty()) {
                Text(
                    text =
                        if (selectedTab == "My Friends")
                            "No Friends added yet"
                        else
                            "No pending friends",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(displayedUsers) { user ->
                        FriendUserCard(
                            user = user,
                            showAddButton = selectedTab == "Pending Friends",
                            onAddFriend = {
                                if (myFriends.none { it.email == user.email }) {
                                    myFriends.add(user)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FriendTabButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(46.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFF2F80ED) else Color.White,
            contentColor = if (selected) Color.White else Color(0xFF2F80ED)
        )
    ) {
        Text(text, fontSize = 13.sp)
    }
}

@Composable
fun FriendUserCard(
    user: User,
    showAddButton: Boolean,
    onAddFriend: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.name ?: "Unknown User",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = user.email ?: "No email",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Attending: ${user.attendingEvent ?: "No event Selected"}",
                    fontSize = 13.sp,
                    color = Color(0xFF2F80ED),
                    fontWeight = FontWeight.Medium
                )
            }
            if (showAddButton) {
                Button(
                    onClick = onAddFriend,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Add")
                }
            }
        }
    }
}