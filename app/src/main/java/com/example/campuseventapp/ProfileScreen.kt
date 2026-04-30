package com.example.campuseventapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
fun ProfileScreen(navController: NavController, isAdvisor: Boolean, email: String = "") {
    var name by remember { mutableStateOf("") }
    var club by remember { mutableStateOf("") }
    var editingName by remember { mutableStateOf(false) }
    var editingClub by remember { mutableStateOf(false) }
    var tempName by remember { mutableStateOf(name) }
    var tempClub by remember { mutableStateOf(club) }

    val userViewModel: UserListViewModel = viewModel()
    val users by userViewModel.users.collectAsState()

    LaunchedEffect(users) {
        val user = users.find { it.email == email }
        if (user != null) {
            name = user.name ?: ""
            club = user.club ?: ""
            tempName = name
            tempClub = club
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(Color(0xFF2F80ED), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile Picture",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (name.isBlank()) "User Profile" else name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF202028)
            )

            Text(
                text = if (isAdvisor) "Club Advisor" else "Student",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    if (editingName) {
                        OutlinedTextField(
                            value = tempName,
                            onValueChange = { tempName = it },
                            label = { Text("Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                TextButton(onClick = { name = tempName; editingName = false }) {
                                    Text("Save", color = Color(0xFF31C15B))
                                }
                            }
                        )
                    } else {
                        EditableProfileRow(label = "Name", value = name, onEdit = { tempName = name; editingName = true })
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    ProfileInfoRow(label = "Email", value = email)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    ProfileInfoRow(label = "Role", value = if (isAdvisor) "Club Advisor" else "Student")
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    if (editingClub) {
                        OutlinedTextField(
                            value = tempClub,
                            onValueChange = { tempClub = it },
                            label = { Text("Club") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                TextButton(onClick = { club = tempClub; editingClub = false }) {
                                    Text("Save", color = Color(0xFF31C15B))
                                }
                            }
                        )
                    } else {
                        EditableProfileRow(label = "Club", value = club, onEdit = { tempClub = club; editingClub = true })
                    }

                    if (!isAdvisor) {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                        Text(
                            text = "Subscribed Categories",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF202028)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        listOf("Career", "Tech", "Wellness").forEach { category ->
                            var subscribed by remember { mutableStateOf(SubscriptionState.isSubscribed(category)) }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = category, fontSize = 14.sp, color = Color.Gray)
                                Switch(
                                    checked = subscribed,
                                    onCheckedChange = {
                                        subscribed = it
                                        SubscriptionState.toggle(category)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
            ) {
                Text("Log Out", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun EditableProfileRow(label: String, value: String, onEdit: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF202028))
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(onClick = onEdit, contentPadding = PaddingValues(0.dp)) {
                Text("Edit", fontSize = 12.sp, color = Color(0xFF2F80ED))
            }
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF202028))
    }
}