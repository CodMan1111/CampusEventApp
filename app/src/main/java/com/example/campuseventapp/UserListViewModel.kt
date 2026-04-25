package com.example.campuseventapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserListViewModel : ViewModel() {

    private val repository = EventRepository()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            try {
                _users.value = repository.getUsers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}