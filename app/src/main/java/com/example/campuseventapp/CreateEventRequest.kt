package com.example.campuseventapp

data class CreateEventRequest(
    val title: String,
    val category: String,
    val description: String,
    val dateTime: String,
    val location: String,
    val creatorId: String = "admin"
)