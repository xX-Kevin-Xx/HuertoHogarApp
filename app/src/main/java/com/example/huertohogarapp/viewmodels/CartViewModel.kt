package com.example.huertohogarapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarapp.data.models.CartItem
import com.example.huertohogarapp.data.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CartRepository(application)

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems
    val cartItemCount = cartItems.map { list ->
        list.sumOf { it.quantity }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )


    init {
        loadCart()
    }

    private fun loadCart() {
        viewModelScope.launch {
            _cartItems.value = repository.getCartItems()
        }
    }

    fun addItem(item: CartItem) {
        viewModelScope.launch {
            repository.addToCart(item)
            loadCart()
        }
    }

    fun removeItem(productId: String) {
        viewModelScope.launch {
            repository.deleteByProductId(productId)
            loadCart()
        }
    }



    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
            loadCart()
        }
    }

    fun getTotal(): Double {
        return _cartItems.value.sumOf { it.price * it.quantity }
    }
}
