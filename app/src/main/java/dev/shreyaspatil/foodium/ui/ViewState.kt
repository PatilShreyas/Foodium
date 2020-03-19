package dev.shreyaspatil.foodium.ui

/**
 * State Management for UI & Data
 */
sealed class ViewState<T>

class Loading<T> : ViewState<T>()

data class Success<T>(val data: T) : ViewState<T>()

data class Error<T>(val message: String) : ViewState<T>()
