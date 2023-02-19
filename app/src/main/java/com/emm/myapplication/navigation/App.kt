package com.emm.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.emm.myapplication.navigation.routes.Route
import com.emm.myapplication.screens.home.HomeScreen
import com.emm.myapplication.screens.login.LoginScreen

@Composable
fun App() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.LoginRoute.route) {
        composable(Route.LoginRoute.route) {
            LoginScreen()
        }
        composable(Route.HomeRoute.route) {
            HomeScreen()
        }
    }

}