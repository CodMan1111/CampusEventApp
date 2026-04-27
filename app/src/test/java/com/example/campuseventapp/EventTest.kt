package com.example.campuseventapp

import org.junit.Assert.assertEquals
import org.junit.Test

class EventTest {
    @Test
    fun eventStoresCorrectValue() {
        val event = Event (
            eventID = "101",
            title = "AI Workshop",
            category = "Tech",
            description = "Learn about AI",
            dateTime = "4/15/2026 10:00 AM",
            location = "School of Business"
        )
        assertEquals("101", event.eventID)
        assertEquals("AI Workshop", event.title)
        assertEquals("Tech", event.category)
        assertEquals("Learn about AI", event.description)
        assertEquals("4/15/2026 10:00 AM", event.dateTime)
        assertEquals("School of Business", event.location)
    }
}