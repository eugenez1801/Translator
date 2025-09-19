package com.example.translator.common

sealed class Resource<T> {
    data class Success<T>(val data: T): Resource<T>()
    data class Error(val error: String): Resource<String>()
    class Loading<T>: Resource<T>()
}