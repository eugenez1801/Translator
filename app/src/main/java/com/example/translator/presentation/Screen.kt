package com.example.translator.presentation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object MainScreen: Screen()

    @Serializable
    object FavouritesScreen: Screen()
}