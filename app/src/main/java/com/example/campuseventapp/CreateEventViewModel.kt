package com.example.campuseventapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateEventViewModel : ViewModel() {

    private val repository = EventRepository()

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun createEvent(
        title: String,
        category: String,
        description: String,
        dateTime: String,
        location: String
    ) {
        if (!validateForm(title, dateTime, location)) {
            _error.value = "Please fill in all required fields"
            return
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true
                val request = CreateEventRequest(
                    title = title,
                    category = category,
                    description = description,
                    dateTime = dateTime,
                    location = location
                )
                repository.createEvent(request)
                _isSuccess.value = true
            } catch (e: Exception) {
                _error.value = e.message
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun validateForm(title: String, dateTime: String, location: String): Boolean {
        return title.isNotBlank() && dateTime.isNotBlank() && location.isNotBlank()
    }
}