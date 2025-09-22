package com.example.translator.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.translator.presentation.favourites_screen.FavouritesScreen
import com.example.translator.presentation.main_screen.MainScreen
import com.example.translator.ui.theme.TranslatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TranslatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets.systemBars) { innerPadding ->
                    val navController = rememberNavController()
                    val viewModel = hiltViewModel<MainViewModel>()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.MainScreen,
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable<Screen.MainScreen> {
                            MainScreen(navController, viewModel)
                        }

                        composable<Screen.FavouritesScreen> {
                            FavouritesScreen(navController, viewModel)
                        }
                    }
                }
            }
        }
    }
}