package com.example.campuseventapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val viewModel: EventListViewModel = viewModel()
            StudentHomeScreen(
                viewModel = viewModel,
                onEventClick = { eventID ->
                    navController.navigate("eventDetail/$eventID")
                }
            )
        }
        composable(
            route = "eventDetail/{eventID}",
            arguments = listOf(navArgument("eventID") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventID = backStackEntry.arguments?.getString("eventID") ?: ""
            val viewModel: EventDetailViewModel = viewModel()
            viewModel.loadEvent(eventID)
            EventDetailScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}