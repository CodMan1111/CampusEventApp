package com.example.campuseventapp

data class Event(
    val eventID: String = "",
    val title: String? = null,
    val category: String? = null,
    val dateTime: String? = null,
    val description: String? = null,
    val location: String? = null,
    val createdAt: String? = null,
    val creatorId: String? = null,
    val imageUrl: String? = null
)