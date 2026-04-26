package com.example.campuseventapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = EventRepository()

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    init {
        loadEvents()
    }

    fun loadEvents() {
        viewModelScope.launch {
            try {
                val loaded = repository.getEvents()
                _events.value = loaded

                loaded.forEach { event ->
                    val category = event.category ?: return@forEach
                    if (SubscriptionState.isSubscribed(category)) {
                        NotificationHelper.sendNotification(
                            getApplication(),
                            "New ${category} Event!",
                            event.title ?: "A new event has been posted"
                        )
                    }
                }
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