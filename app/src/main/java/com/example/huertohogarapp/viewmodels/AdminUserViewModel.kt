package com.example.huertohogarapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarapp.data.remote.RetrofitClient
import com.example.huertohogarapp.data.remote.dto.UserDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminUserViewModel : ViewModel() {

    private val _users = MutableStateFlow<List<UserDto>>(emptyList())
    val users = _users.asStateFlow()

    private val _selectedUser = MutableStateFlow<UserDto?>(null)
    val selectedUser = _selectedUser.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private var token: String? = null

    fun loadUsers(authToken: String) {
        token = authToken

        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                val response = RetrofitClient.userApi.getUsers()
                _users.value = response

            } catch (e: Exception) {
                _error.value = "Error cargando usuarios: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun loadUserById(id: Long, token: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                val auth = "Bearer $token"
                val user = RetrofitClient.userApi.getUserById(auth, id)

                _selectedUser.value = user

            } catch (e: Exception) {
                _error.value = "Error cargando usuario: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateUserRole(id: Long, newRole: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                val authHeader = "Bearer ${token ?: return@launch}"

                val updatedUser = selectedUser.value?.copy(rol = newRole)
                    ?: return@launch

                RetrofitClient.userApi.updateUser(id, updatedUser)

                _users.value = _users.value.map {
                    if (it.id == id) updatedUser else it
                }

            } catch (e: Exception) {
                _error.value = "Error actualizando usuario: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteUser(id: Long, token: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                RetrofitClient.userApi.deleteUser(id, "Bearer $token")

                _users.value = _users.value.filterNot { it.id == id }

            } catch (e: Exception) {
                _error.value = "Error eliminando usuario: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

}
