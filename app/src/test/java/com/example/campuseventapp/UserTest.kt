package com.example.campuseventapp

import org.junit.Assert.assertEquals
import org.junit.Test

class UserTest {
    @Test
    fun userStoresCorrectValue() {
        val user = User(
            id = "1",
            email = "lucas.deutsch@quinnipiac.edu",
            name = "Lucas Deutsch",
            role = "Student",
            club = null,
            attendingEvent = "Career Fair"
        )
        assertEquals("1", user.id)
        assertEquals("lucas.deutsch@quinnipiac.edu", user.email)
        assertEquals("Lucas Deutsch", user.name)
        assertEquals("Student", user.role)
        assertEquals("Career Fair", user.attendingEvent)
    }
}