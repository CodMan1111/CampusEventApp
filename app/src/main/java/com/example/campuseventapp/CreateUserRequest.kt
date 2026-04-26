package com.example.campuseventapp

data class CreateUserRequest(
    val email: String,
    val name: String,
    val role: String = "Student",
    val club: String = ""
)