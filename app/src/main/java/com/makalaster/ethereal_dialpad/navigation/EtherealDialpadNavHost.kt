package com.makalaster.ethereal_dialpad.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makalaster.ethereal_dialpad.main.HomeView
import com.makalaster.ethereal_dialpad.pads.Pad
import com.makalaster.ethereal_dialpad.pads.draw.DrawPad
import com.makalaster.ethereal_dialpad.pads.draw.DrawViewModel
import com.makalaster.ethereal_dialpad.pads.flat.FlatPad
import com.makalaster.ethereal_dialpad.pads.flat.FlatViewModel
import com.makalaster.ethereal_dialpad.pads.swarm.SwarmPad
import com.makalaster.ethereal_dialpad.pads.swarm.SwarmViewModel

@Composable
fun EtherealDialpadNavHost(
    navController: NavHostController,
    toggleSystemBars: (Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = Modifier,
    ) {
        composable(route = Home.route) {
            HomeView(
                onPadClick = navController::navigateSingleTopTo
            )
            toggleSystemBars(true)
        }
        composable(route = FlatPad.route) {
            Pad(viewModel = hiltViewModel<FlatViewModel>()) { _, width, height, onTap ->
                FlatPad(width = width, height = height, onTap = onTap)
                toggleSystemBars(false)
            }
        }
        composable(route = DrawPad.route) {
            Pad(viewModel = hiltViewModel<DrawViewModel>()) { _, width, height, onTap ->
                DrawPad(width = width, height = height, onTap = {
                    onTap()
                })
                toggleSystemBars(false)
            }
        }
        composable(route = SwarmPad.route) {
            Pad(viewModel = hiltViewModel<SwarmViewModel>()) { _, width, height, onTap ->
                SwarmPad(width = width, height = height, onTap = {
                    onTap()
                })
                toggleSystemBars(false)
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