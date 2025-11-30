package com.example.huertohogarapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarapp.data.remote.RetrofitClient
import com.example.huertohogarapp.data.remote.dto.UserDto
import com.example.huertohogarapp.data.session.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminUserViewModel : ViewModel() {

    private val _users = MutableStateFlow<List<UserDto>>(emptyList())
    val users = _users.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun loadUsers(token: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                val response = RetrofitClient.userApi.getUsers("Bearer $token")
                _users.value = response

            } catch (e: Exception) {
                _error.value = "Error cargando usuarios: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}

