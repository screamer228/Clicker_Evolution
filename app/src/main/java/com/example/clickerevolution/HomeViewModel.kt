package com.example.clickerevolution

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _resources = MutableStateFlow(0)
    val resourcesFlow: StateFlow<Int> = _resources

    // Функция для увеличения количества ресурсов
    fun incrementResources() {
        _resources.value += 1
    }
}