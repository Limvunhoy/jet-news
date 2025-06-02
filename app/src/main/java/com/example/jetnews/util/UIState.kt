package com.example.jetnews.util

sealed interface UIState<out R> {
    object Loading : UIState<Nothing>
    data class Success<T>(val data: T) : UIState<T>
    data class Error(val message: String, val code: String) : UIState<Nothing>
}