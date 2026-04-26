package com.example.campuseventapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    val emailOrUsername = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val selectedAccountType = remember { mutableStateOf("Student") }
    var loginError by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Login to Campus Event Notifier",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF202028)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Please sign in using your Quinnipiac email:\nFirstName.LastName@quinnipiac.edu",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Password: 12345",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = emailOrUsername.value,
                    onValueChange = { emailOrUsername.value = it },
                    label = { Text("Email or Username") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )
                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text("Password") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))

                if (loginError.isNotEmpty()) {
                    Text(text = loginError, color = Color.Red, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Button(
                    onClick = {
                        val emailPattern = Regex("^[A-Za-z]+\\.[A-Za-z]+@quinnipiac\\.edu$")
                        if (!emailPattern.matches(emailOrUsername.value.trim())) {
                            loginError = "Unable to login. Use: FirstName.LastName@quinnipiac.edu"
                        } else {
                            loginError = ""
                            val nameParts = emailOrUsername.value
                                .substringBefore("@")
                                .split(".")
                            val name = if (nameParts.size >= 2)
                                "${nameParts[0].replaceFirstChar { it.uppercase() }} ${nameParts[1].replaceFirstChar { it.uppercase() }}"
                            else emailOrUsername.value

                            userViewModel.createUser(
                                email = emailOrUsername.value,
                                name = name,
                                role = selectedAccountType.value,
                                club = ""
                            )

                            AppState.currentUserEmail = emailOrUsername.value

                            if (selectedAccountType.value == "Student") {
                                navController.navigate(Screen.StudentHome.route + "?email=${emailOrUsername.value}") {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                }
                            } else {
                                navController.navigate(Screen.AdvisorHome.route) {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF31C15B))
                ) {
                    Text(text = "Log In", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Choose Account Type",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF444444)
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { selectedAccountType.value = "Student" },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedAccountType.value == "Student") Color(0xFF2F80ED) else Color.White,
                            contentColor = if (selectedAccountType.value == "Student") Color.White else Color(0xFF2F80ED)
                        )
                    ) { Text("Student") }
                    Button(
                        onClick = { selectedAccountType.value = "Club Advisor" },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedAccountType.value == "Club Advisor") Color(0xFF2F80ED) else Color.White,
                            contentColor = if (selectedAccountType.value == "Club Advisor") Color.White else Color(0xFF2F80ED)
                        )
                    ) { Text("Club Advisor") }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    Text("Login Preview")
}