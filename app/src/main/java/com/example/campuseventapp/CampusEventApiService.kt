package com.example.campuseventapp

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CampusEventApiService {
    @GET("campus_events/")
    suspend fun getEvents(): List<Event>

    @POST("campus_events/")
    suspend fun createEvent(@Body event: CreateEventRequest): Event

    @GET("user/")
    suspend fun getUsers(): List<User>
}