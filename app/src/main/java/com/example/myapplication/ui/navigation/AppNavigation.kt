package com.example.todolistnavigation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.screen.CatBreedsScreen
import com.example.myapplication.ui.screen.DetailScreen
import com.example.myapplication.viewmodel.CatViewModel

@Composable
fun AppNavigation(viewModel: CatViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "list") {
        composable("list") {
            CatBreedsScreen(viewModel, navController)
        }
        composable(
            route = "detail/{breedId}",
            arguments = listOf(navArgument("breedId") { type = NavType.StringType })
        ) { backStackEntry ->
            val breedId = backStackEntry.arguments?.getString("breedId") ?: return@composable
            DetailScreen( breedId, viewModel)
        }
    }
}
