package com.emm.noteapp.navigation.routes

sealed class Route(val route: String) {

    object LoginRoute : Route("LoginRoute")
    object HomeRoute : Route("HomeRoute")
}
