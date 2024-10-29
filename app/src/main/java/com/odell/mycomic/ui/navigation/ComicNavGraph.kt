package com.odell.mycomic.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.odell.mycomic.ui.home.HomeDestination
import com.odell.mycomic.ui.home.HomeMain
import com.odell.mycomic.ui.screens.ComicDetailsDestination
import com.odell.mycomic.ui.screens.ComicDetailsScreen
import com.odell.mycomic.ui.screens.ComicEditDestination
import com.odell.mycomic.ui.screens.ComicEditScreen
import com.odell.mycomic.ui.screens.ComicEntryDestination
import com.odell.mycomic.ui.screens.ComicEntryScreen


/**
 * Provides Navigation graph for the application.
 */
@Composable
fun ComicNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeMain(
                navigateToItemEntry = { navController.navigate(ComicEntryDestination.route) },
                navigateToItemUpdate = {
                    navController.navigate("${ComicDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = ComicEntryDestination.route) {
            ComicEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = ComicDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ComicDetailsDestination.COMIC_ID_ARG) {
                type = NavType.IntType
            })
        ) {
            ComicDetailsScreen(
                navigateToEditItem = { navController.navigate("${ComicEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = ComicEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ComicEditDestination.COMIC_ID_ARG) {
                type = NavType.IntType
            })
        ) {
            ComicEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}

