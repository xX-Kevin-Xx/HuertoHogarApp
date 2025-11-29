package com.example.huertohogarapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarapp.data.models.User
import com.example.huertohogarapp.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository(application)

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    fun login(email: String) {
        viewModelScope.launch {
            _currentUser.value = repository.getUserByEmail(email)
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
            _currentUser.value = user
        }
    }
}
