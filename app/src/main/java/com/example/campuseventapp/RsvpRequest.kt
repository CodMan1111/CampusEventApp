package com.example.campuseventapp

data class RsvpRequest(
    val userId: String,
    val eventId: String,
    val eventTitle: String,
    val status: String
)