package com.makalaster.etherealdialpad.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makalaster.etherealdialpad.main.HomeView
import com.makalaster.etherealdialpad.pads.Pad
import com.makalaster.etherealdialpad.pads.draw.DrawPad
import com.makalaster.etherealdialpad.pads.draw.DrawViewModel
import com.makalaster.etherealdialpad.pads.flat.FlatPad
import com.makalaster.etherealdialpad.pads.flat.FlatViewModel
import com.makalaster.etherealdialpad.pads.swarm.SwarmPad
import com.makalaster.etherealdialpad.pads.swarm.SwarmViewModel

@Composable
fun EtherealDialpadNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = Modifier
    ) {
        composable(route = Home.route) {
            HomeView(
                onPadClick = navController::navigateSingleTopTo
            )
        }
        composable(route = FlatPad.route) {
            Pad(viewModel = hiltViewModel<FlatViewModel>()) { width, height ->
                FlatPad(width = width, height = height)
            }
        }
        composable(route = DrawPad.route) {
            Pad(viewModel = hiltViewModel<DrawViewModel>()) { width, height ->
                DrawPad(width = width, height = height)
            }
        }
        composable(route = SwarmPad.route) {
            Pad(hiltViewModel<SwarmViewModel>()) {width, height ->
                SwarmPad(width = width, height = height)
            }
        }
//        composable(route = GridPad.route) {
//            GridPad()
//        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
    }