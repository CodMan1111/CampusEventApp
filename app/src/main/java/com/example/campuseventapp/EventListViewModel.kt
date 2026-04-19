package com.example.campuseventapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventListViewModel : ViewModel() {

    private val repository = EventRepository()

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    init {
        loadEvents()
    }

    fun loadEvents() {
        viewModelScope.launch {
            try {
                _events.value = repository.getEvents()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun filterByCategory(cat: String) {
        viewModelScope.launch {
            try {
                val all = repository.getEvents()
                _events.value = if (cat == "All") all else all.filter { it.category == cat }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun searchEvents(query: String) {
        viewModelScope.launch {
            try {
                val all = repository.getEvents()
                _events.value = all.filter {
                    it.title?.contains(query, ignoreCase = true) == true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}