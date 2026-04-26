package com.example.campuseventapp

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CampusEventApiService {
    @GET("campus_events/")
    suspend fun getEvents(): List<Event>

    @POST("campus_events/")
    suspend fun createEvent(@Body event: CreateEventRequest): Event

    @GET("user/")
    suspend fun getUsers(): List<User>
    @POST("user/")
    suspend fun createUser(@Body user: CreateUserRequest): User

    @POST("rsvp/")
    suspend fun rsvpEvent(@Body request: RsvpRequest): Any

    @POST("friends/")
    suspend fun addFriend(@Body request: FriendRequest): Any

    @GET("friends/")
    suspend fun getFriends(@Query("userId") userId: String): List<Friendship>
}