package com.example.clickerevolution.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _resources = MutableStateFlow(0)
    val resourcesFlow: StateFlow<Int> = _resources

    fun incrementResources() {
        _resources.value += 1
    }
}