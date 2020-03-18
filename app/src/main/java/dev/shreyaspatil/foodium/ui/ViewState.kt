package dev.shreyaspatil.foodium.ui

sealed class ViewState<T>

class Loading<T> : ViewState<T>()

data class Success<T>(val data: T) : ViewState<T>()

data class Error<T>(val message: String) : ViewState<T>()
