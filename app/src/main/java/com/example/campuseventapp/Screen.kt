package com.example.campuseventapp

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object StudentHome : Screen("student_home")
    object StudentSearch : Screen("student_search")
    object StudentFriends : Screen("student_friends")
    object StudentAlerts : Screen("student_alerts")
    object StudentProfile : Screen("student_profile")

    object AdvisorHome : Screen("advisor_home")
    object AdvisorSearch : Screen("advisor_search")
    object CreateEvent : Screen("create_event")
    object AdvisorAlerts : Screen("advisor_alerts")
    object AdvisorProfile : Screen("advisor_profile")
}