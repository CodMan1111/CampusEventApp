package com.example.campuseventapp

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Friends : Screen("friends")
    object Alerts : Screen("alerts")
    object Profile : Screen("profile")
}