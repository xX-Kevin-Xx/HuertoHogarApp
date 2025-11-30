package com.example.huertohogarapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarapp.data.remote.RetrofitClient
import com.example.huertohogarapp.data.remote.dto.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPrefs = application.getSharedPreferences("auth_prefs", 0)

    private val _token = MutableStateFlow<String?>(sharedPrefs.getString("jwt_token", null))
    val token = _token.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun login(correo: String, password: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.authApi.login(
                    LoginRequest(correo, password)
                )

                // Guardamos token
                sharedPrefs.edit()
                    .putString("jwt_token", response.token)
                    .apply()

                _token.value = response.token
                _error.value = null

            } catch (e: Exception) {
                _error.value = "Error al iniciar sesión: ${e.message}"
            }
        }
    }

    // ---------------------
    // 🔥 CERRAR SESIÓN
    // ---------------------
    fun signOut() {
        sharedPrefs.edit().clear().apply()
        _token.value = null
    }
}
