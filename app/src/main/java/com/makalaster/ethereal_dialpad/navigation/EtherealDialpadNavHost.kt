package com.makalaster.ethereal_dialpad.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makalaster.ethereal_dialpad.about.AboutScreen
import com.makalaster.ethereal_dialpad.R
import com.makalaster.ethereal_dialpad.main.HomeView
import com.makalaster.ethereal_dialpad.pads.Pad
import com.makalaster.ethereal_dialpad.pads.draw.DrawPad
import com.makalaster.ethereal_dialpad.pads.draw.DrawViewModel
import com.makalaster.ethereal_dialpad.pads.flat.FlatPad
import com.makalaster.ethereal_dialpad.pads.flat.FlatViewModel
import com.makalaster.ethereal_dialpad.pads.grid.GridPad
import com.makalaster.ethereal_dialpad.pads.grid.GridViewModel
import com.makalaster.ethereal_dialpad.pads.swarm.SwarmPad
import com.makalaster.ethereal_dialpad.pads.swarm.SwarmViewModel

@Composable
fun EtherealDialpadNavHost(
    navController: NavHostController,
    toggleSystemBars: (Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = Modifier,
    ) {
        fun goBack() {
            navController.popBackStack()
        }

        composable<Home> {
            HomeView(
                onPadClick = navController::navigateSingleTopTo,
                onAboutClick = { navController.navigate(About) }
            )
            toggleSystemBars(true)
        }
        composable<FlatPad> {
            Pad(viewModel = hiltViewModel<FlatViewModel>(), R.string.flat_pad, { goBack() }) { _, width, height, onTap ->
                FlatPad(width = width, height = height, onTap = onTap)
                toggleSystemBars(false)
            }
        }
        composable<DrawPad> {
            Pad(viewModel = hiltViewModel<DrawViewModel>(), R.string.draw_pad, { goBack() }) { _, width, height, onTap ->
                DrawPad(width = width, height = height, onTap = onTap)
                toggleSystemBars(false)
            }
        }
        composable<SwarmPad> {
            Pad(viewModel = hiltViewModel<SwarmViewModel>(), R.string.swarm_pad, { goBack() }) { _, width, height, onTap ->
                SwarmPad(width = width, height = height, onTap = onTap)
                toggleSystemBars(false)
            }
        }
        composable<GridPad> {
            Pad(viewModel = hiltViewModel<GridViewModel>(), padTitle = R.string.grid_pad, onBackPressed = { goBack() }) { _, width, height, onTap ->
                GridPad(width = width, height = height, onTap = onTap)
                toggleSystemBars(false)
            }
        }

        composable<About> {
            AboutScreen(
                onBackPressed = ::goBack
            )
            toggleSystemBars(true)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: EtherealDialpadDestination) =
    this.navigate(route) {
        launchSingleTop = true
    }