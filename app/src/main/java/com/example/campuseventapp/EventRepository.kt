package com.example.campuseventapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventRepository {
    private val api = Retrofit.Builder()
        .baseUrl("https://ecnyx67wh9.execute-api.us-east-1.amazonaws.com/dev/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CampusEventApiService::class.java)

    suspend fun getEvent(eventId: String): Event? {
        return api.getEvents().find { it.eventID == eventId }
    }

    suspend fun getEvents(): List<Event> {
        return api.getEvents()
    }

    suspend fun createEvent(request: CreateEventRequest): Event {
        return api.createEvent(request)
    }
    suspend fun getUsers(): List<User> {
        return api.getUsers()
    }
}