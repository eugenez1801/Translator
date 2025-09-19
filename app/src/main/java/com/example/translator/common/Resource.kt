package com.example.translator.common

sealed class Resource {
    data class Success<T>(val data: T): Resource()
    data class Error(val error: String): Resource()
    object Loading: Resource()
}