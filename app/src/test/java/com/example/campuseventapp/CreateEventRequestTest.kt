package com.example.campuseventapp

import org.junit.Assert.assertEquals
import org.junit.Test

class CreateEventRequestTest {
    @Test
    fun createEventRequestStoresCorrectValues() {
        val request = CreateEventRequest (
            title = "Career Fair",
            category = "Career",
            description = "Meet employers",
            dateTime = "4/25/2026 10:00 AM",
            location = "Innovation Lab"
        )
        assertEquals("Career Fair", request.title)
        assertEquals("Career", request.category)
        assertEquals("Meet employers", request.description)
        assertEquals("4/25/2026 10:00 AM", request.dateTime)
        assertEquals("Innovation Lab", request.location)
    }
}