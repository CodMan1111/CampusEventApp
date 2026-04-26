package com.example.campuseventapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FriendsViewModel : ViewModel() {

    private val repository = EventRepository()

    private val _friendships = MutableStateFlow<List<Friendship>>(emptyList())
    val friendships: StateFlow<List<Friendship>> = _friendships

    fun loadFriends(userId: String) {
        viewModelScope.launch {
            try {
                _friendships.value = repository.getFriends(userId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addFriend(userId1: String, userId2: String) {
        viewModelScope.launch {
            try {
                repository.addFriend(FriendRequest(userId1, userId2))
                loadFriends(userId1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}