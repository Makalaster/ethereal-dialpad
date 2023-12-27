package com.makalaster.etherealdialpad.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makalaster.etherealdialpad.main.HomeView
import com.makalaster.etherealdialpad.pads.BasicPad

@Composable
fun EtherealDialpadNavHost(
    navController: NavHostController,
    onSettingsClick: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = Modifier
    ) {
        composable(route = Home.route) {
            HomeView(
                onPadClick = navController::navigateSingleTopTo,
                onSettingsClick = onSettingsClick
            )
        }
        composable(route = BasicPad.route) {
            BasicPad()
        }
        composable(route = DrawPad.route) {

        }
        composable(route = SwarmPad.route) {

        }
        composable(route = GridPad.route) {

        }
        composable(route = Settings.route) {

        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
    }