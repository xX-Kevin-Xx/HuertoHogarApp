package com.example.huertohogarapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarapp.data.remote.RetrofitClient
import com.example.huertohogarapp.data.remote.dto.LoginRequest
import com.example.huertohogarapp.data.remote.dto.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPrefs = application.getSharedPreferences("auth_prefs", 0)

    private val _token = MutableStateFlow<String?>(sharedPrefs.getString("jwt_token", null))
    val token = _token.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess = _registerSuccess.asStateFlow()

    private val _role = MutableStateFlow<String?>(null)
    val role = _role.asStateFlow()


    fun login(correo: String, password: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.authApi.login(
                    LoginRequest(
                        correo = correo,
                        password = password
                    )
                )

                val token = response.token
                val role = response.rol

                _token.value = token
                _role.value = role
                _error.value = null

            } catch (e: Exception) {

                if (e.message?.contains("401") == true) {
                    _error.value = "NO_AUTH"
                } else {
                    _error.value = "Error al iniciar sesión"
                }
            }
        }
    }

    fun register(
        nombre: String,
        correo: String,
        password: String,
        telefono: String,
        direccion: String
    ) {
        viewModelScope.launch {
            try {
                _error.value = null
                _registerSuccess.value = false

                val req = RegisterRequest(
                    nombre = nombre,
                    correo = correo,
                    password = password,
                    telefono = telefono,
                    direccion = direccion
                )

                RetrofitClient.authApi.register(req)

                _registerSuccess.value = true

            } catch (e: Exception) {
                _error.value = "Error al registrar: ${e.message}"
            }
        }
    }

    fun signOut() {
        sharedPrefs.edit().clear().apply()
        _token.value = null
    }

    fun setAuthError(message: String) {
        _error.value = message
    }

    fun logout() {
        _token.value = null
        _role.value = null
        _error.value = null
        RetrofitClient.setToken("")
    }

    fun clearError() {
        _error.value = null
    }



}
