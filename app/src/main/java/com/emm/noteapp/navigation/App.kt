package com.emm.noteapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.emm.noteapp.navigation.routes.Route
import com.emm.noteapp.screens.home.HomeScreen
import com.emm.noteapp.screens.login.LoginScreen

@Composable
fun App() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.LoginRoute.route) {
        composable(Route.LoginRoute.route) {
            LoginScreen(navController)
        }
        composable(Route.HomeRoute.route) {
            HomeScreen()
        }
    }

}