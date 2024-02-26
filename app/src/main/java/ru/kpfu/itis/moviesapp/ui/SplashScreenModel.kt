package ru.kpfu.itis.moviesapp.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenModel : ScreenModel {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    init {
        onInit()
    }

    private fun onInit() = screenModelScope.launch {
        _isLoading.value = true
        delay(2000)
        _isLoading.value = false
    }
}
