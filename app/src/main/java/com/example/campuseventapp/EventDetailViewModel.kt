package com.example.campuseventapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventDetailViewModel : ViewModel() {

    private val repository = EventRepository()

    private val _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> = _event

    private val _rsvpStatus = MutableStateFlow<String>("")
    val rsvpStatus: StateFlow<String> = _rsvpStatus

    fun loadEvent(id: String) {
        viewModelScope.launch {
            try {
                _event.value = repository.getEvent(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun rsvpToEvent(status: String, eventId: String, eventTitle: String) {
        _rsvpStatus.value = status
        viewModelScope.launch {
            try {
                repository.rsvpEvent(
                    RsvpRequest(
                        userId = AppState.currentUserEmail,
                        eventId = eventId,
                        eventTitle = eventTitle,
                        status = status
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}