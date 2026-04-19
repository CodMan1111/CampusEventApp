package com.example.campuseventapp
import retrofit2.http.GET

interface CampusEventApiService {
    @GET("campus_events/")
    suspend fun getEvents(): List<Event>
}