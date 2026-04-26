package com.example.campuseventapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.StudentHome.route + "?email={email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            StudentHomeScreen(
                navController = navController,
                email = email,
                onEventClick = { eventId ->
                    navController.navigate("event_detail/$eventId")
                }
            )
        }
        composable(Screen.StudentSearch.route) {
            SearchScreen(navController = navController, isAdvisor = false)
        }
        composable(Screen.StudentFriends.route) {
            FriendsScreen(navController)
        }
        composable(Screen.StudentAlerts.route) {
            AlertsScreen(navController = navController, isAdvisor = false)
        }
        composable(Screen.StudentProfile.route) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ProfileScreen(navController = navController, isAdvisor = false, email = email)
        }
        composable(Screen.AdvisorHome.route) {
            AdvisorBrowseEventsScreen(navController = navController)
        }
        composable(Screen.AdvisorSearch.route) {
            SearchScreen(navController = navController, isAdvisor = true)
        }
        composable(Screen.CreateEvent.route) {
            CreateEventScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AdvisorAlerts.route) {
            AlertsScreen(navController = navController, isAdvisor = true)
        }
        composable(Screen.AdvisorProfile.route) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ProfileScreen(navController = navController, isAdvisor = true, email = email)
        }
        composable("event_detail/{eventId}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: return@composable
            val viewModel: EventDetailViewModel = viewModel(backStackEntry)
            LaunchedEffect(eventId) {
                viewModel.loadEvent(eventId)
            }
            EventDetailScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}