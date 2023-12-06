package com.proco.app

sealed class Screens(val route: String) {
    data object Login : Screens(route = "login")
    data object Register : Screens(route = "register")
}
