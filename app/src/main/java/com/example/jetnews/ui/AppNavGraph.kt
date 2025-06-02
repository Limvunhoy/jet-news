package com.example.jetnews.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose.snippets.components.Destination
import com.example.jetnews.ui.home.HomeScreen
import com.example.jetnews.ui.profile.ProfileScreen
import com.example.jetnews.ui.user.UserScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Destination,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route,
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.Home -> HomeScreen()
                    Destination.Profile -> ProfileScreen(navHostController = navController)
                }
            }
        }
        composable(route = "user") {
            UserScreen(navController = navController)
        }
    }
}